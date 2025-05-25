package com.sakuntswingo.bingo;

import android.graphics.Color;
import android.os.Bundle;
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
import java.util.Map;

public class CartierQuiz extends AppCompatActivity {

    TextView questionText, totalQuestionText;
    Button ansA, ansB, ansC, ansD, confirmBtn;
    List<QuestionModel> questionList = new ArrayList<>();
    int currentQuestionIndex = 0;
    int selectedAnswerIndex = -1;
    int correctAnswers = 0;
    boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartier_quiz);

        questionText = findViewById(R.id.question);
        totalQuestionText = findViewById(R.id.total_question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        confirmBtn = findViewById(R.id.action_btn);

        loadQuestionsFromFirebase();

        View.OnClickListener answerClickListener = v -> {
            resetButtonColors();
            selectedAnswerIndex = Integer.parseInt(v.getTag().toString());
            v.setBackgroundColor(Color.parseColor("#FFFACD")); // нежный жёлтый
        };

        ansA.setTag(0);
        ansB.setTag(1);
        ansC.setTag(2);
        ansD.setTag(3);

        ansA.setOnClickListener(answerClickListener);
        ansB.setOnClickListener(answerClickListener);
        ansC.setOnClickListener(answerClickListener);
        ansD.setOnClickListener(answerClickListener);

        confirmBtn.setOnClickListener(v -> {
            if (!answered) {
                if (selectedAnswerIndex == -1) {
                    Toast.makeText(this, "Пожалуйста, выбери ответ", Toast.LENGTH_SHORT).show();
                    return;
                }
                checkAnswer();
            } else {
                currentQuestionIndex++;
                if (currentQuestionIndex < questionList.size()) {
                    showQuestion();
                } else {
                    showResult();
                }
            }
        });
    }

    private void loadQuestionsFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("quizzes").document("cartier").collection("questions")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Map<String, Object> data = doc.getData();
                        String question = (String) data.get("question");
                        List<String> choices = (List<String>) data.get("choices");
                        String correct = (String) data.get("correctAnswer");

                        questionList.add(new QuestionModel(question, choices, correct));
                    }
                    Collections.sort(questionList, (q1, q2) -> q1.getQuestion().compareTo(q2.getQuestion()));
                    showQuestion();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Ошибка загрузки: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    void showQuestion() {
        resetButtonColors();
        answered = false;
        selectedAnswerIndex = -1;
        confirmBtn.setText("Подтвердить");

        QuestionModel q = questionList.get(currentQuestionIndex);
        questionText.setText(q.getQuestion());
        totalQuestionText.setText("Вопрос " + (currentQuestionIndex + 1) + " из " + questionList.size());

        ansA.setText(q.getChoices().get(0));
        ansB.setText(q.getChoices().get(1));
        ansC.setText(q.getChoices().get(2));
        ansD.setText(q.getChoices().get(3));
    }

    void checkAnswer() {
        answered = true;
        QuestionModel q = questionList.get(currentQuestionIndex);
        String correct = q.getCorrectAnswer();

        Button[] buttons = {ansA, ansB, ansC, ansD};
        for (int i = 0; i < buttons.length; i++) {
            String userAnswer = buttons[i].getText().toString();
            if (userAnswer.equals(correct)) {
                buttons[i].setBackgroundColor(Color.parseColor("#66BB6A")); // зелёный
            } else if (i == selectedAnswerIndex) {
                buttons[i].setBackgroundColor(Color.parseColor("#EF5350")); // красный
            }
        }

        if (buttons[selectedAnswerIndex].getText().toString().equals(correct)) {
            correctAnswers++;
        }

        confirmBtn.setText("Следующий вопрос");
    }

    void resetButtonColors() {
        ansA.setBackgroundColor(Color.parseColor("#FFFFFF"));
        ansB.setBackgroundColor(Color.parseColor("#FFFFFF"));
        ansC.setBackgroundColor(Color.parseColor("#FFFFFF"));
        ansD.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    void showResult() {
        String result = correctAnswers >= questionList.size() / 2
                ? "Поздравляем! Вы прошли квиз!"
                : "Увы, вы не прошли квиз.";
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        finish(); // закрываем квиз
    }

    static class QuestionModel {
        private final String question;
        private final List<String> choices;
        private final String correctAnswer;

        public QuestionModel(String question, List<String> choices, String correctAnswer) {
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
