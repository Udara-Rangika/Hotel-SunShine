package com.example.hotelsunshine.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelsunshine.R;
import com.example.hotelsunshine.activity.UpdateRoomActivity;
import com.example.hotelsunshine.db.DBHelper;
import com.example.hotelsunshine.model.Room;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomVH> {

    ArrayList<Room> rooms;
    Context context;

    public RoomAdapter(ArrayList<Room> rooms, Context context) {
        this.rooms = rooms;
        this.context = context;
    }

    @NonNull
    @Override
    public RoomVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_room, parent, false);
        RoomVH rvh = new RoomVH(view);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomVH holder, int position) {

        final Room r = rooms.get(position);
        holder.tvFname.setText(r.getFname());
        holder.tvFname2.setText(r.getFname());
        holder.tvLname.setText(r.getLname());
        holder.tvEmail.setText(r.getEmail());
        holder.tvMobile.setText(String.valueOf(r.getMobile()));
        holder.tvCheckin.setText(r.getCheckin());
        holder.tvCheckout.setText(r.getCheckout());

        holder.cardUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, r.getFname() + " will be updated", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, UpdateRoomActivity.class);
                intent.putExtra("ROOM", r);
                context.startActivity(intent);
            }
        });


        //Delete Data
        holder.cardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, r.getFname() + " will be deleted", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Confirmation !!!");
                builder.setMessage("Are you sure to delete " + r.getFname() + " ?");
                builder.setIcon(android.R.drawable.ic_menu_delete);
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHelper dbHelper = new DBHelper(context);

                        int result = dbHelper.deleteRoom(r.getId());

                        if(result > 0){
                            Toast.makeText(context, "Booking Deleted", Toast.LENGTH_SHORT).show();
                            rooms.remove(r);
                            notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });
    }

    //View Data
    @Override
    public int getItemCount() {
        return rooms.size();
    }

    class RoomVH extends RecyclerView.ViewHolder{

        TextView tvFname, tvFname2, tvLname, tvEmail, tvMobile, tvCheckin, tvCheckout;
        CardView cardUpdate, cardDelete;

        public RoomVH(@NonNull View v) {
            super(v);

            tvFname = v.findViewById(R.id.tvFname);
            tvFname2 = v.findViewById(R.id.tvFname2);
            tvLname = v.findViewById(R.id.tvLname);
            tvEmail = v.findViewById(R.id.tvEmail);
            tvMobile = v.findViewById(R.id.tvMobile);
            tvCheckin = v.findViewById(R.id.tvCheckin);
            tvCheckout = v.findViewById(R.id.tvCheckout);

            cardDelete = v.findViewById(R.id.cardDelete);
            cardUpdate = v.findViewById(R.id.cardUpdate);
        }
    }
}
