package com.example.hotelsunshine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

public class AddRoomActivity extends AppCompatActivity {

    EditText edFname, edLname, edEmail, edMobile, edCheckin, edCheckout;
    Calendar myCalendar;
    boolean isAllFieldsChecked = false;
    FloatingActionButton openMenu;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        edFname = findViewById(R.id.edFname);
        edLname = findViewById(R.id.edLname);
        edEmail = findViewById(R.id.edEmail);
        edMobile = findViewById(R.id.edMobile);
        edCheckin = (EditText) findViewById(R.id.edCheckin);
        edCheckout = (EditText) findViewById(R.id.edCheckout);

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
                new DatePickerDialog(AddRoomActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                new DatePickerDialog(AddRoomActivity.this,date2,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(Calendar myCalendar, EditText edCheckin) {
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        edCheckin.setText(dateFormat.format(myCalendar.getTime()));
    }


    //View Data
    public void showAll(View view) {
        startActivity(new Intent(AddRoomActivity.this, RoomListActivity.class));
    }

    //Add Data
    public void save(View view) {

        isAllFieldsChecked = CheckAllFields();

        if(isAllFieldsChecked) {
            String fname = edFname.getText().toString().trim();
            String lname = edLname.getText().toString().trim();
            String email = edEmail.getText().toString().trim();
            String mobile = edMobile.getText().toString().trim();
            String checkin = edCheckin.getText().toString().trim();
            String checkout = edCheckout.getText().toString().trim();

            DBHelper dbHelper = new DBHelper(AddRoomActivity.this);

            Room r = new Room(fname, lname, email, Integer.parseInt(mobile), checkin, checkout);

            long result = dbHelper.addRoom(r);

            if (result > 0) {
                Toast.makeText(this, "Room Booked " + result, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed " + result, Toast.LENGTH_SHORT).show();
            }
        }
    }


    //Validations
    private boolean CheckAllFields() {
        if(edFname.length() == 0){
            edFname.setError("This field is required");
            return false;
        }
        if(edLname.length() == 0){
            edLname.setError("This field is required");
            return false;
        }
        if (edEmail.length() == 0) {
            edEmail.setError("Email is required");
            return false;
        }
        if(edMobile.length() == 0){
            edMobile.setError("Mobile Number is required");
            return false;
        }
        if(edCheckin.length() == 0){
            edCheckin.setError("This field is required");
            return false;
        }
        if(edCheckout.length() == 0){
            edCheckout.setError("This field is required");
            return false;
        }
        else if(edMobile.length() < 10){
            edMobile.setError("Please enter a valid mobile number");
            return false;
        }
        return true;
    }
}