package com.sakuntswingo.bingo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class VersaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versace);
        Button quizButton = findViewById(R.id.quizButton);
        quizButton.setOnClickListener(view -> {
            Intent intent = new Intent(VersaceActivity.this, VersaceQuiz.class);
            startActivity(intent);
        });
    }
}
