// File: AdminAddRoomActivity.java
package com.example.bookme;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class AdminAddRoomActivity extends AppCompatActivity {

    private EditText editTextRoomID, editTextRoomName, editTextCapacity, editTextLocation;
    private Button buttonAddRoom;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_room);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance("https://bookme-9de6f-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("StudyRooms");

        // Initialize views
        editTextRoomID = findViewById(R.id.editTextRoomID);
        editTextRoomName = findViewById(R.id.editTextRoomName);
        editTextCapacity = findViewById(R.id.editTextCapacity);
        editTextLocation = findViewById(R.id.editTextLocation);
        buttonAddRoom = findViewById(R.id.buttonAddRoom);

        // Set up button click listener
        buttonAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRoom();
            }
        });
    }

    private void addRoom() {
        String roomID = editTextRoomID.getText().toString().trim();
        String roomName = editTextRoomName.getText().toString().trim();
        String capacity = editTextCapacity.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(roomID)) {
            editTextRoomID.setError("Room ID is required");
            return;
        }
        if (TextUtils.isEmpty(roomName)) {
            editTextRoomName.setError("Room Name is required");
            return;
        }
        if (TextUtils.isEmpty(capacity)) {
            editTextCapacity.setError("Capacity is required");
            return;
        }
        if (TextUtils.isEmpty(location)) {
            editTextLocation.setError("Location is required");
            return;
        }

        // Store room data in Firebase
        Map<String, Object> roomData = new HashMap<>();
        roomData.put("roomID", roomID);
        roomData.put("roomName", roomName);
        roomData.put("capacity", Integer.parseInt(capacity));
        roomData.put("location", location);

        // Push to Firebase
        databaseReference.child(roomID).setValue(roomData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(AdminAddRoomActivity.this, "Room added successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(AdminAddRoomActivity.this, "Failed to add room", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        editTextRoomID.setText("");
        editTextRoomName.setText("");
        editTextCapacity.setText("");
        editTextLocation.setText("");
    }
}
