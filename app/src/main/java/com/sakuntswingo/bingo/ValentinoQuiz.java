package com.sakuntswingo.bingo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ValentinoQuiz extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn, logoutBtn;

    int score = 0;
    int totalQuestion = ValentinoQuestions.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valentino_quiz);

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);
        logoutBtn = findViewById(R.id.logout_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions : " + totalQuestion);

        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.logout_btn) {
            logOutAndGoToRegister();
        } else if (view.getId() == R.id.submit_btn) {
            if (!answered) return;

            currentQuestionIndex++;
            loadNewQuestion();
            answered = false;
        } else {
            if (answered) return;

            Button clickedButton = (Button) view;
            selectedAnswer = clickedButton.getText().toString();

            String correctAnswer = ValentinoQuestions.correctAnswers[currentQuestionIndex];

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

        questionTextView.setText(ValentinoQuestions.question[currentQuestionIndex]);
        ansA.setText(ValentinoQuestions.choices[currentQuestionIndex][0]);
        ansB.setText(ValentinoQuestions.choices[currentQuestionIndex][1]);
        ansC.setText(ValentinoQuestions.choices[currentQuestionIndex][2]);
        ansD.setText(ValentinoQuestions.choices[currentQuestionIndex][3]);

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

    private void logOutAndGoToRegister() {
        Intent intent = new Intent(ValentinoQuiz.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
    private void navigateToProfile() {
        Intent intent = new Intent(ValentinoQuiz.this, ProfileActivity.class);  // Ensure this is your correct ProfileActivity
        startActivity(intent);
        finish();
    }
}
