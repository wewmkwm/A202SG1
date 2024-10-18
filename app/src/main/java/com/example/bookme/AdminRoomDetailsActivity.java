package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminRoomDetailsActivity extends AppCompatActivity {

    private EditText editTextRoomID, editTextRoomName, editTextCapacity, editTextLocation;
    private Button buttonEdit, buttonDelete;

    private String roomID;
    private DatabaseReference roomRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_room_details);

        // Initialize UI components
        editTextRoomID = findViewById(R.id.editTextRoomID);
        editTextRoomName = findViewById(R.id.editTextRoomName);
        editTextCapacity = findViewById(R.id.editTextCapacity);
        editTextLocation = findViewById(R.id.editTextLocation);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonDelete = findViewById(R.id.buttonDelete);

        // Get the selected room ID from intent
        roomID = getIntent().getStringExtra("roomID");

        // Reference to Firebase Database
        roomRef = FirebaseDatabase.getInstance().getReference("StudyRooms").child(roomID);

        // Load the room details
        loadRoomDetails();

        // Edit button click listener
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRoomDetails();
            }
        });

        // Delete button click listener
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteRoom();
            }
        });
    }

    private void loadRoomDetails() {
        roomRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    editTextRoomID.setText(dataSnapshot.child("roomID").getValue(String.class));
                    editTextRoomName.setText(dataSnapshot.child("roomName").getValue(String.class));
                    editTextCapacity.setText(String.valueOf(dataSnapshot.child("capacity").getValue(Long.class)));
                    editTextLocation.setText(dataSnapshot.child("location").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdminRoomDetailsActivity.this, "Failed to load room details.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editRoomDetails() {
        String newRoomID = editTextRoomID.getText().toString().trim();
        String newRoomName = editTextRoomName.getText().toString().trim();
        String newCapacity = editTextCapacity.getText().toString().trim();
        String newLocation = editTextLocation.getText().toString().trim();

        if (TextUtils.isEmpty(newRoomID) || TextUtils.isEmpty(newRoomName) || TextUtils.isEmpty(newCapacity) || TextUtils.isEmpty(newLocation)) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        roomRef.child("roomID").setValue(newRoomID);
        roomRef.child("roomName").setValue(newRoomName);
        roomRef.child("capacity").setValue(Integer.parseInt(newCapacity));
        roomRef.child("location").setValue(newLocation);

        Toast.makeText(this, "Room details updated.", Toast.LENGTH_SHORT).show();
    }

    private void confirmDeleteRoom() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Room");
        builder.setMessage("Are you sure you want to delete this room?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteRoom();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void deleteRoom() {
        roomRef.removeValue();
        Toast.makeText(this, "Room deleted.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
