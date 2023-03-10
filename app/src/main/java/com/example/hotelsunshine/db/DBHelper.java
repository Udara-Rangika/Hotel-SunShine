package com.example.hotelsunshine.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hotelsunshine.model.Room;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "rooms.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE tbl_room (id INTEGER PRIMARY KEY AUTOINCREMENT, fname TEXT, lname TEXT, email TEXT, mobile INTEGER, checkin TEXT, checkout TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_room");
        onCreate(sqLiteDatabase);
    }

    public long addRoom(Room r) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fname", r.getFname());
        cv.put("lname", r.getLname());
        cv.put("email", r.getEmail());
        cv.put("mobile", r.getMobile());
        cv.put("checkin", r.getCheckin());
        cv.put("checkout", r.getCheckout());

        return db.insert("tbl_room", null, cv);

    }

    public ArrayList<Room> getAllRooms(){
        ArrayList<Room> rooms = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tbl_room", null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String fname = cursor.getString(1);
                String lname = cursor.getString(2);
                String email = cursor.getString(3);
                int mobile = cursor.getInt(4);
                String checkin = cursor.getString(5);
                String checkout = cursor.getString(6);

                Room r = new Room(id, fname, lname, email, mobile, checkin, checkout);
                rooms.add(r);

            }while (cursor.moveToNext());
        }

        return rooms;
    }

    public int updateRoom(Room r) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("fname", r.getFname());
        cv.put("lname", r.getLname());
        cv.put("email", r.getEmail());
        cv.put("mobile", r.getMobile());
        cv.put("checkin", r.getCheckin());
        cv.put("checkout", r.getCheckout());

         return db.update("tbl_room", cv, "id=?", new String[]{String.valueOf(r.getId())});
    }

    public int deleteRoom(int id) {

        SQLiteDatabase db = getWritableDatabase();
        return db.delete("tbl_room", "id=?", new String[]{ String.valueOf(id) });

    }
}
