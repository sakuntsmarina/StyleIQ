package com.sakuntswingo.bingo;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VersaceQuiz extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button actionBtn;
    ImageButton backBtn;

    int score = 0;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    boolean answered = false;
    List<QuizQuestion> questions = new ArrayList<>();
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versace_quiz);

        db = FirebaseFirestore.getInstance();

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        actionBtn = findViewById(R.id.action_btn);
        backBtn = findViewById(R.id.back_btn);

        backBtn.setOnClickListener(v -> navigateToProfile());

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);

        actionBtn.setOnClickListener(v -> {
            if (!answered) {
                if (selectedAnswer.isEmpty()) {
                    Toast.makeText(this, "Пожалуйста, выберите ответ", Toast.LENGTH_SHORT).show();
                    return;
                }
                showAnswer();
                answered = true;
                actionBtn.setText("Следующий вопрос");
            } else {
                currentQuestionIndex++;
                updateQuestionNumberDisplay();
                loadNewQuestion();
                answered = false;
                actionBtn.setText("Подтвердить");
            }
        });

        loadQuestions();
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
                            List<String> choices = new ArrayList<>((List<String>) document.get("choices"));
                            String correctAnswer = document.getString("correctAnswer");
                            Collections.shuffle(choices);
                            questions.add(new QuizQuestion(question, choices, correctAnswer));
                        }
                        updateQuestionNumberDisplay();
                        loadNewQuestion();
                    } else {
                        Toast.makeText(this, "Не удалось загрузить вопросы", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (answered) return;
        Button clickedButton = (Button) view;
        resetButtonColors();
        clickedButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFACD"))); // Нежно-желтый
        selectedAnswer = clickedButton.getText().toString();
    }

    private void showAnswer() {
        String correctAnswer = questions.get(currentQuestionIndex).getCorrectAnswer();

        if (ansA.getText().toString().equals(selectedAnswer)) {
            ansA.setBackgroundTintList(ColorStateList.valueOf(selectedAnswer.equals(correctAnswer) ? Color.GREEN : Color.RED));
        } else if (ansB.getText().toString().equals(selectedAnswer)) {
            ansB.setBackgroundTintList(ColorStateList.valueOf(selectedAnswer.equals(correctAnswer) ? Color.GREEN : Color.RED));
        } else if (ansC.getText().toString().equals(selectedAnswer)) {
            ansC.setBackgroundTintList(ColorStateList.valueOf(selectedAnswer.equals(correctAnswer) ? Color.GREEN : Color.RED));
        } else if (ansD.getText().toString().equals(selectedAnswer)) {
            ansD.setBackgroundTintList(ColorStateList.valueOf(selectedAnswer.equals(correctAnswer) ? Color.GREEN : Color.RED));
        }

        if (!selectedAnswer.equals(correctAnswer)) {
            if (ansA.getText().toString().equals(correctAnswer)) {
                ansA.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            } else if (ansB.getText().toString().equals(correctAnswer)) {
                ansB.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            } else if (ansC.getText().toString().equals(correctAnswer)) {
                ansC.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            } else if (ansD.getText().toString().equals(correctAnswer)) {
                ansD.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            }
        }

        if (selectedAnswer.equals(correctAnswer)) score++;
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

        resetButtonColors();
        selectedAnswer = "";
    }

    void resetButtonColors() {
        ansA.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        ansB.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        ansC.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        ansD.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
    }

    void finishQuiz() {
        String passStatus = (score > questions.size() * 0.6) ? "Пройдено" : "Не пройдено";

        new android.app.AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Очки: " + score + " из " + questions.size())
                .setPositiveButton("Перезапустить", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadQuestions();
    }

    private void navigateToProfile() {
        Intent intent = new Intent(VersaceQuiz.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateQuestionNumberDisplay() {
        totalQuestionsTextView.setText("Вопрос " + (currentQuestionIndex + 1) + " из " + questions.size());
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