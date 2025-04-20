package com.sakuntswingo.bingo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValentinoQuiz extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn, logoutBtn, backBtn;

    int score = 0;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    boolean answered = false;
    List<QuizQuestion> questions = new ArrayList<>();
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valentino_quiz);

        db = FirebaseFirestore.getInstance();

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);
        logoutBtn = findViewById(R.id.logout_btn);
        backBtn = findViewById(R.id.back_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

        backBtn.setOnClickListener(v -> navigateToProfile());

        loadQuestions();
    }

    private void loadQuestions() {
        db.collection("quizzes")
                .document("valentino")
                .collection("questions")
                .orderBy("index")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        questions.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String question = document.getString("question");
                            List<String> choices = new ArrayList<>((List<String>) document.get("choices"));
                            String correctAnswer = document.getString("correctAnswer");
                            // Shuffle choices
                            Collections.shuffle(choices);
                            questions.add(new QuizQuestion(question, choices, correctAnswer));
                        }
                        // Shuffle questions
                        Collections.shuffle(questions);
                        totalQuestionsTextView.setText("Total questions : " + questions.size());
                        Log.d("ValentinoQuiz", "Loaded and shuffled " + questions.size() + " questions");
                        for (int i = 0; i < questions.size(); i++) {
                            Log.d("ValentinoQuiz", "Question " + i + ": " + questions.get(i).getQuestion());
                            Log.d("ValentinoQuiz", "Choices: " + questions.get(i).getChoices());
                        }
                        loadNewQuestion();
                    } else {
                        Log.e("ValentinoQuiz", "Failed to load questions: " + task.getException().getMessage());
                        Toast.makeText(ValentinoQuiz.this, "Failed to load questions", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.logout_btn) {
            logOutAndGoToProfile();
        } else if (view.getId() == R.id.submit_btn) {
            if (!answered) return;

            currentQuestionIndex++;
            loadNewQuestion();
            answered = false;
        } else {
            if (answered) return;

            Button clickedButton = (Button) view;
            selectedAnswer = clickedButton.getText().toString();

            String correctAnswer = questions.get(currentQuestionIndex).getCorrectAnswer();

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
        if (questions.isEmpty() || currentQuestionIndex >= questions.size()) {
            finishQuiz();
            return;
        }

        QuizQuestion currentQuestion = questions.get(currentQuestionIndex);
        questionTextView.setText(currentQuestion.getQuestion());
        ansA.setText(currentQuestion.getChoices().get(0));
        ansB.setText(currentQuestion.getChoices().get(1));
        ansC.setText(currentQuestion.getChoices().get(2));
        ansD.setText(currentQuestion.getChoices().get(3));

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        selectedAnswer = "";
    }

    void finishQuiz() {
        String passStatus = (score > questions.size() * 0.6) ? "Passed" : "Failed";

        new android.app.AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " + score + " out of " + questions.size())
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadQuestions(); // Reload and reshuffle questions
    }

    private void logOutAndGoToProfile() {
        Intent intent = new Intent(ValentinoQuiz.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToProfile() {
        Intent intent = new Intent(ValentinoQuiz.this, ProfileActivity.class);
        startActivity(intent);
        finish();
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