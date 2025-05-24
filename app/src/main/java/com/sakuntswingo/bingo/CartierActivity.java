package com.sakuntswingo.bingo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CartierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartier);

        Button quizButton = findViewById(R.id.quizButton);
        quizButton.setOnClickListener(view -> {
            Intent intent = new Intent(CartierActivity.this, CartierQuiz.class);
            startActivity(intent);
        });
    }
}
