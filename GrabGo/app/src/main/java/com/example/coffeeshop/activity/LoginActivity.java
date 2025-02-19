package com.example.coffeeshop.activity;
import com.example.coffeeshop.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

        private EditText etEmail, etPassword;
        private Button btnLogin;
        private TextView tvRegister;
        private FirebaseAuth mAuth;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);  // Replace with your actual layout file

            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();

            // Bind UI elements from the XML layout
            etEmail = findViewById(R.id.etEmail);
            etPassword = findViewById(R.id.etPassword);
            btnLogin = findViewById(R.id.btnLogin);
            tvRegister = findViewById(R.id.tvRegister);

            // Set OnClickListener for the Login button
            btnLogin.setOnClickListener(v -> loginUser());

            // Initialize the Admin Panel button
            Button btnAdminPanel = findViewById(R.id.adminbutton);

            // Set click listener to open AdminActivity
            btnAdminPanel.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(intent);
            });

            // Set OnClickListener for the Register TextView to redirect to Register screen
            tvRegister.setOnClickListener(v -> {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);

            });
        }

        private void loginUser() {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validate the inputs
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Firebase Authentication: Sign in with email and password
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign-in successful, move to the Home activity
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            // If sign-in fails, show a toast message
                            Toast.makeText(LoginActivity.this, "Authentication failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        @Override
        protected void onStart() {
            super.onStart();
            // Check if the user is already signed in
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                // If the user is already logged in, go directly to HomeActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

}


