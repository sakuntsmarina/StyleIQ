package com.sakuntswingo.bingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Кнопка Versace
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, VersaceActivity.class);
                startActivity(intent);
            }
        });

        // Кнопка Yves Saint Laurent
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Здесь можно добавить Intent для новой активности
            }
        });

        // Кнопка Valentino
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ValentinoActivity.class);
                startActivity(intent);
            }
        });

        // Кнопка Chanel
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Пример перехода на другую активность
                Intent intent = new Intent(ProfileActivity.this, ChanelActivity.class);
                startActivity(intent);
            }
        });


        // Кнопка Dior
        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, DiorActivity.class);
                startActivity(intent);
            }
        });
    }
}
