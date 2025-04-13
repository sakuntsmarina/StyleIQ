package com.sakuntswingo.bingo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DiorQuiz extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn, logoutBtn, backBtn;  // Added backBtn

    int score = 0;
    int totalQuestion = DiorQuestions.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dior_quiz); // Ensure this layout is correct

        // Initialize views
        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);
        logoutBtn = findViewById(R.id.logout_btn);
        backBtn = findViewById(R.id.back_btn);  // Initialize back button

        // Set onClick listeners
        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

        // Set the total number of questions
        totalQuestionsTextView.setText("Total questions : " + totalQuestion);

        // Load the first question
        loadNewQuestion();

        // Back button click listener
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProfile();  // Navigate to ProfileActivity
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.logout_btn) {
            logOutAndGoToProfile();  // Navigate to ProfileActivity when logging out
        } else if (view.getId() == R.id.submit_btn) {
            if (!answered) return;

            currentQuestionIndex++;
            loadNewQuestion();
            answered = false;
        } else {
            if (answered) return;

            Button clickedButton = (Button) view;
            selectedAnswer = clickedButton.getText().toString();

            String correctAnswer = DiorQuestions.correctAnswers[currentQuestionIndex];

            if (selectedAnswer.equals(correctAnswer)) {
                clickedButton.setBackgroundColor(Color.GREEN);
                score++;
            } else {
                clickedButton.setBackgroundColor(Color.RED);

                if (ansA.getText().toString().equals(correctAnswer)) {
                    ansA.setBackgroundColor(Color.GREEN);
                } else if (ansB.getText().toString().equals(correctAnswer)) {
                    ansB.setBackgroundColor(Color.GREEN);
                } else if (ansC.getText().toString().equals(correctAnswer)) {
                    ansC.setBackgroundColor(Color.GREEN);
                } else if (ansD.getText().toString().equals(correctAnswer)) {
                    ansD.setBackgroundColor(Color.GREEN);
                }
            }

            answered = true;
        }
    }

    void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }

        questionTextView.setText(DiorQuestions.question[currentQuestionIndex]);
        ansA.setText(DiorQuestions.choices[currentQuestionIndex][0]);
        ansB.setText(DiorQuestions.choices[currentQuestionIndex][1]);
        ansC.setText(DiorQuestions.choices[currentQuestionIndex][2]);
        ansD.setText(DiorQuestions.choices[currentQuestionIndex][3]);

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        selectedAnswer = "";
    }

    void finishQuiz() {
        String passStatus = (score > totalQuestion * 0.6) ? "Passed" : "Failed";

        new android.app.AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

    private void logOutAndGoToProfile() {
        Intent intent = new Intent(DiorQuiz.this, ProfileActivity.class); // Navigate to ProfileActivity
        startActivity(intent);
        finish();
    }

    // Method to navigate to ProfileActivity
    private void navigateToProfile() {
        Intent intent = new Intent(DiorQuiz.this, ProfileActivity.class);  // Ensure this is your correct ProfileActivity
        startActivity(intent);
        finish();  // Close DiorQuiz activity to remove it from the stack
    }
}
