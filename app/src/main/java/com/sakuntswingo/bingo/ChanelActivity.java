package com.sakuntswingo.bingo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chanel); // Убедись, что у тебя есть такой layout

        Button quizButton = findViewById(R.id.quizButton);
        quizButton.setOnClickListener(view -> {
            Intent intent = new Intent(ChanelActivity.this, ChanelQuiz.class);
            startActivity(intent);
        });
    }
}