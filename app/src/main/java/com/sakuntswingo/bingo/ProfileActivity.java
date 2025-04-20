package com.sakuntswingo.bingo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private List<QuizQuestion> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Кнопка Versace
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, VersaceActivity.class);
            startActivity(intent);
        });

        // Кнопка Yves Saint Laurent
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            // Здесь можно добавить Intent для новой активности
            // Intent intent = new Intent(ProfileActivity.this, YvesSaintLaurentActivity.class);
            // startActivity(intent);
        });

        // Кнопка Valentino
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ValentinoActivity.class);
            startActivity(intent);
        });

        // Кнопка Chanel
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ChanelActivity.class);
            startActivity(intent);
        });

        // Кнопка Dior
        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, DiorActivity.class);
            startActivity(intent);
        });

        // Кнопка Logout
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> {
            auth.signOut();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close ProfileActivity
        });
    }

    private void loadQuestions() {
        db.collection("quizzes")
                .document("versace")
                .collection("questions")
                .orderBy("index")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        questions.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String question = document.getString("question");
                            List<String> choices = (List<String>) document.get("choices");
                            String correctAnswer = document.getString("correctAnswer");
                            Log.d("ProfileActivity", "Question: " + question);
                            questions.add(new QuizQuestion(question, choices, correctAnswer));
                        }
                        Log.d("ProfileActivity", "Loaded " + questions.size() + " questions");
                    } else {
                        Log.e("ProfileActivity", "Failed to load questions: " + task.getException().getMessage());
                        Toast.makeText(ProfileActivity.this, "Failed to load questions", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private static class QuizQuestion {
        private String question;
        private List<String> choices;
        private String correctAnswer;

        public QuizQuestion(String question, List<String> choices, String correctAnswer) {
            this.question = question;
            this.choices = choices;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() {
            return question;
        }

        public List<String> getChoices() {
            return choices;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }
}