package com.example.coffeeshop.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.coffeeshop.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    private EditText etLatitude, etLongitude;
    private Button btnUpdateLocation;
    private DatabaseReference databaseRef;
    private String orderId = "orderId123"; // Replace with actual order ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        btnUpdateLocation = findViewById(R.id.btnUpdateLocation);
        databaseRef = FirebaseDatabase.getInstance().getReference("orders").child(orderId);

        Button btnBack = findViewById(R.id.backbutton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closes AdminActivity and returns to the previous page
            }
        });
        btnUpdateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocation();
            }
        });
    }
    private void updateLocation() {
        String latStr = etLatitude.getText().toString().trim();
        String lngStr = etLongitude.getText().toString().trim();

        if (!latStr.isEmpty() && !lngStr.isEmpty()) {
            double latitude = Double.parseDouble(latStr);
            double longitude = Double.parseDouble(lngStr);

            // Update only delivery location
            databaseRef.child("delivery_lat").setValue(latitude);
            databaseRef.child("delivery_lng").setValue(longitude);
            databaseRef.child("status").setValue("On the way");

            Toast.makeText(this, "Location Updated Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Enter valid coordinates", Toast.LENGTH_SHORT).show();
        }
    }

}