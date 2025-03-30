package com.sakuntswingo.bingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        editTextUsername = findViewById(R.id.username);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirm_password);
        progressBar = findViewById(R.id.progressBar);

        // Register button listener
        Button buttonRegister = findViewById(R.id.btn_reg);
        buttonRegister.setOnClickListener(v -> validateAndRegisterUser());

        // Link to Login Activity (when clicked)
        TextView linkLogin = findViewById(R.id.link_login);
        linkLogin.setOnClickListener(v -> {
            // Show a toast for debugging purposes
            Toast.makeText(RegisterActivity.this, "Redirecting to Login", Toast.LENGTH_SHORT).show();

            // Create an intent to navigate to LoginActivity
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);  // Start the LoginActivity
            finish();  // Optionally close RegisterActivity
        });
    }

    private void validateAndRegisterUser() {
        // Your validation and registration logic here...
    }
}
