package com.sakuntswingo.bingo;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FirebaseUploader {

    public static void uploadCartierQuestions() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String[] questions = CartierQuestions.question;
        String[][] choices = CartierQuestions.choices;
        String[] correctAnswers = CartierQuestions.correctAnswers;

        for (int i = 0; i < questions.length; i++) {
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("question", questions[i]);
            questionData.put("choices", Arrays.asList(choices[i]));
            questionData.put("correctAnswer", correctAnswers[i]);
            questionData.put("index", i);

            db.collection("quizzes")
                    .document("cartier")
                    .collection("questions")
                    .document(String.valueOf(i))
                    .set(questionData)
                    .addOnSuccessListener(aVoid -> Log.d("FirebaseUploader", "Question uploaded successfully"))
                    .addOnFailureListener(e -> Log.e("FirebaseUploader", "Error uploading question: " + e.getMessage()));
        }
    }
}