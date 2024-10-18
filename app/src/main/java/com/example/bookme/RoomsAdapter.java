// File: RoomsAdapter.java
package com.example.bookme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Map;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.RoomViewHolder> {

    private ArrayList<Map<String, String>> roomList;

    public RoomsAdapter(ArrayList<Map<String, String>> roomList) {
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_admin, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Map<String, String> room = roomList.get(position);
        holder.textViewRoomID.setText("ID: " + room.get("roomID"));
        holder.textViewRoomName.setText("Name: " + room.get("roomName"));
        holder.textViewCapacity.setText("Capacity: " + room.get("capacity"));
        holder.textViewLocation.setText("Location: " + room.get("location"));
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRoomID, textViewRoomName, textViewCapacity, textViewLocation;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRoomID = itemView.findViewById(R.id.textViewRoomID);
            textViewRoomName = itemView.findViewById(R.id.textViewRoomName);
            textViewCapacity = itemView.findViewById(R.id.textViewCapacity);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
        }
    }
}
