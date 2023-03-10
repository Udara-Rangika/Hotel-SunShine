package com.example.hotelsunshine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hotelsunshine.R;
import com.example.hotelsunshine.adapter.RoomAdapter;
import com.example.hotelsunshine.db.DBHelper;
import com.example.hotelsunshine.model.Room;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class RoomListActivity extends AppCompatActivity {

    TextView tvTotal;
    RecyclerView recyclerView;
    RoomAdapter roomAdapter;
    ArrayList<Room> rooms;
    DBHelper dbHelper;
    FloatingActionButton openMenu;
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        recyclerView = findViewById(R.id.recyclerView);
        tvTotal = findViewById(R.id.tvTotal);

        dbHelper = new DBHelper(this);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        openMenu = findViewById(R.id.float1);

        openMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        rooms = dbHelper.getAllRooms();
        tvTotal.setText("Total Rooms : " + rooms.size());

        roomAdapter = new RoomAdapter(rooms, this);
        recyclerView.setAdapter(roomAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(llm);
    }
}