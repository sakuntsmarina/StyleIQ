package com.sakuntswingo.bingo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    private ProgressBar progressBar;
    private TextView loginLink;
    private FirebaseAuth auth;

    private static final String TAG = "RegisterActivity";

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
        loginLink = findViewById(R.id.link_login);
        auth = FirebaseAuth.getInstance();

        // Set the registration button's click listener
        Button buttonRegister = findViewById(R.id.btn_reg);
        buttonRegister.setOnClickListener(v -> validateAndRegisterUser());

        loginLink.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void validateAndRegisterUser() {
        String textUsername = editTextUsername.getText().toString().trim();
        String textEmail = editTextEmail.getText().toString().trim();
        String textPassword = editTextPassword.getText().toString().trim();
        String textConfirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Validate user inputs
        if (TextUtils.isEmpty(textUsername)) {
            showError(editTextUsername, "Username is required");
        } else if (TextUtils.isEmpty(textEmail)) {
            showError(editTextEmail, "Email is required");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
            showError(editTextEmail, "Valid email is required");
        } else if (TextUtils.isEmpty(textPassword)) {
            showError(editTextPassword, "Password is required");
        } else if (textPassword.length() < 6) {
            showError(editTextPassword, "Password should be at least 6 characters");
        } else if (TextUtils.isEmpty(textConfirmPassword)) {
            showError(editTextConfirmPassword, "Confirm your password");
        } else if (!textPassword.equals(textConfirmPassword)) {
            showError(editTextConfirmPassword, "Passwords do not match");
        } else {
            // Proceed with registration
            progressBar.setVisibility(View.VISIBLE);
            registerUser(textUsername, textEmail, textPassword);
        }
    }

    private void showError(EditText editText, String error) {
        editText.setError(error);
        editText.requestFocus();
    }

    private void registerUser(String username, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        if (firebaseUser != null) {
                            // Send verification email first
                            firebaseUser.sendEmailVerification()
                                    .addOnCompleteListener(emailTask -> {
                                        if (emailTask.isSuccessful()) {
                                            // Save user details after successful email verification send
                                            saveUserDetailsToDatabase(firebaseUser, username, email);
                                            Toast.makeText(RegisterActivity.this,
                                                    "Registration successful! Please check your email for verification.",
                                                    Toast.LENGTH_LONG).show();
                                            // Redirect to LoginActivity
                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(RegisterActivity.this,
                                                    "Failed to send verification email.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        handleRegistrationError(task);
                    }
                });
    }

    private void saveUserDetailsToDatabase(FirebaseUser firebaseUser, String username, String email) {
        User user = new User(username, email);
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");

        referenceProfile.child(firebaseUser.getUid()).setValue(user)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this,
                                "Failed to save user data.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleRegistrationError(@NonNull Task<AuthResult> task) {
        try {
            throw task.getException();
        } catch (FirebaseAuthUserCollisionException e) {
            showError(editTextEmail, "Email already in use");
        } catch (FirebaseAuthInvalidCredentialsException e) {
            showError(editTextEmail, "Invalid email format");
        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.GONE);
    }
}