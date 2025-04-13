package com.sakuntswingo.bingo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ValentinoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valentino); // Убедись, что у тебя есть такой layout

        Button quizButton = findViewById(R.id.quizButton);
        quizButton.setOnClickListener(view -> {
            Intent intent = new Intent(ValentinoActivity.this, ValentinoQuiz.class);
            startActivity(intent);
        });
    }
}
