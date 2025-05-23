package com.sakuntswingo.bingo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLogEmail, editTextLogPassword;
    private ProgressBar progressBar;
    private TextView goRegister;
    private FirebaseAuth authProfile;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
goRegister = findViewById(R.id.goBackRegister);
goRegister.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
});
        // Initialize views and check if user is already logged in
        initializeViews();
        checkIfUserLoggedIn();
    }

    private void initializeViews() {
        // Initialize UI elements
        editTextLogEmail = findViewById(R.id.email_log);
        editTextLogPassword = findViewById(R.id.password_log);
        progressBar = findViewById(R.id.progressBar_log);
        authProfile = FirebaseAuth.getInstance();

        // Set up the login button click listener
        Button buttonLogin = findViewById(R.id.btn_log);
        buttonLogin.setOnClickListener(v -> validateAndLogin());
    }

    private void checkIfUserLoggedIn() {
        // Check if a user is already logged in and if their email is verified
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null && firebaseUser.isEmailVerified()) {
            redirectToMainActivity();
        }
    }

    private void validateAndLogin() {
        String textEmail = editTextLogEmail.getText().toString().trim();
        String textPassword = editTextLogPassword.getText().toString().trim();

        // Validate the email and password inputs
        if (isValidInput(textEmail, textPassword)) {
            progressBar.setVisibility(View.VISIBLE);
            loginUser(textEmail, textPassword);
        }
    }

    private boolean isValidInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            editTextLogEmail.setError("Email is required");
            editTextLogEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextLogEmail.setError("Valid email is required");
            editTextLogEmail.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(password)) {
            editTextLogPassword.setError("Password is required");
            editTextLogPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void loginUser(String email, String password) {
        // Perform the login with Firebase Authentication
        authProfile.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = authProfile.getCurrentUser();
                if (firebaseUser != null) {
                    if (firebaseUser.isEmailVerified()) {
                        redirectToMainActivity();
                    } else {
                        sendVerificationEmail(firebaseUser);
                    }
                }
            } else {
                handleLoginError(task);
            }
        });
    }

    private void redirectToMainActivity() {
        // Redirect to ProfileActivity if login is successful
        Toast.makeText(LoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
        finish();
    }

    private void sendVerificationEmail(FirebaseUser firebaseUser) {
        // Send an email verification if the email is not verified
        firebaseUser.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this,
                                "Verification email sent! Please check your inbox.",
                                Toast.LENGTH_LONG).show();
                        authProfile.signOut();
                        showEmailVerificationDialog();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Failed to send verification email. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleLoginError(Task<AuthResult> task) {
        // Handle different types of login errors
        try {
            throw task.getException();
        } catch (FirebaseAuthInvalidUserException | FirebaseAuthInvalidCredentialsException e) {
            editTextLogEmail.setError("Invalid email or password");
            editTextLogEmail.requestFocus();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(LoginActivity.this, "User login failed", Toast.LENGTH_SHORT).show();
    }

    private void showEmailVerificationDialog() {
        // Show an alert dialog prompting the user to verify their email
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email Verification Required")
                .setMessage("A verification email has been sent to your email address. Please verify your email before logging in.")
                .setPositiveButton("Open Email", (dialog, which) -> openEmailApp())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .create()
                .show();
    }

    private void openEmailApp() {
        // Open the default email app for the user to verify their email
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_EMAIL);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
        }
    }
}