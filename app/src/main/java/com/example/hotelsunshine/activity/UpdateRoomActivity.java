package com.example.hotelsunshine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hotelsunshine.R;
import com.example.hotelsunshine.db.DBHelper;
import com.example.hotelsunshine.model.Room;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateRoomActivity extends AppCompatActivity {

    EditText edFname, edLname, edEmail, edMobile, edCheckin, edCheckout;
    int id;
    Calendar myCalendar;

    FloatingActionButton openMenu;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);

        Room r = (Room) getIntent().getExtras().getSerializable("ROOM");

        id = r.getId();
        edFname = findViewById(R.id.edFname);
        edLname = findViewById(R.id.edLname);
        edEmail = findViewById(R.id.edEmail);
        edMobile = findViewById(R.id.edMobile);
        edCheckin = (EditText) findViewById(R.id.edCheckin);
        edCheckout = (EditText) findViewById(R.id.edCheckout);

        edFname.setText(r.getFname());
        edLname.setText(r.getLname());
        edEmail.setText(r.getEmail());
        edMobile.setText(String.valueOf(r.getMobile()));
        edCheckin.setText(r.getCheckin());
        edCheckout.setText(r.getCheckout());

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


        //Date Picker
        myCalendar= Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(myCalendar, edCheckin);
            }
        };
        edCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(UpdateRoomActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        DatePickerDialog.OnDateSetListener date2 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(myCalendar, edCheckout);
            }
        };
        edCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(UpdateRoomActivity.this,date2,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(Calendar myCalendar, EditText edCheckin) {
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        edCheckin.setText(dateFormat.format(myCalendar.getTime()));
    }

    //Update Data
    public void update(View view) {
        String fname = edFname.getText().toString().trim();
        String lname = edLname.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String mobile = edMobile.getText().toString().trim();
        String checkin = edCheckin.getText().toString().trim();
        String checkout = edCheckout.getText().toString().trim();

        Room r = new Room(id, fname, lname, email, Integer.parseInt(mobile), checkin, checkout);

        DBHelper dbHelper = new DBHelper(this);
        int result = dbHelper.updateRoom(r);

        if(result > 0){
            Toast.makeText(this,"Booking Updated " + result, Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(this, "Failed " + result, Toast.LENGTH_SHORT).show();
        }
    }
}