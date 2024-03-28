package com.example.finalproject;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.Question;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanetQuiz extends AppCompatActivity {

    private TextView textViewQuestion;
    private RadioGroup radioGroupOptions;
    private Button buttonNext;
    private TextView textViewResults;

    private int currentQuestionIndex = 0;
    private int score = 0;
    private int totalQuestions = 0;
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_quiz);

        // Initialize views
        textViewQuestion = findViewById(R.id.textViewQuestion);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        buttonNext = findViewById(R.id.buttonNext);
        textViewResults = findViewById(R.id.textViewResults);

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the current activity
            }
        });

        // Load quiz questions
        questions = loadQuestions();

        // Display the first question
        showQuestion();

        // Set click listener for the "Next" button
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if an option is selected
                int selectedOptionId = radioGroupOptions.getCheckedRadioButtonId();
                if (selectedOptionId == -1) {
                    Toast.makeText(PlanetQuiz.this, "Please select an option", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get the selected option
                RadioButton selectedRadioButton = findViewById(selectedOptionId);
                String selectedOption = selectedRadioButton.getText().toString();

                // Check if the selected option is correct
                if (isCorrectAnswer(selectedOption)) {
                    // Increment score or handle correct answer logic
                    Toast.makeText(PlanetQuiz.this, "Correct!", Toast.LENGTH_SHORT).show();
                    score++;
                } else {
                    Toast.makeText(PlanetQuiz.this, "Incorrect!", Toast.LENGTH_SHORT).show();

                }

                // Move to the next question
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    showQuestion();
                } else {
                    // Quiz completed, show result or navigate to result activity
                    showResults();
                    Toast.makeText(PlanetQuiz.this, "Quiz completed!", Toast.LENGTH_SHORT).show();
                    // Update the user's final score in Firestore
                    updateFinalScoreInFirestore(score);
                }
                
            }

            private void updateFinalScoreInFirestore(int score) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> scoreData = new HashMap<>();
                scoreData.put("FINAL_RESULT", score);
                db.collection("QUIZ").document("lpopsOt5zsxU2ePII03y").update(scoreData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Score updated successfully");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating score", e);
                            }
                        });
            }
        });
    }

    private void showQuestion() {
        // Display the current question and options
        Question currentQuestion = questions.get(currentQuestionIndex);
        textViewQuestion.setText(currentQuestion.getQuestion());
        List<String> options = currentQuestion.getOptions();
        radioGroupOptions.removeAllViews(); // Clear any previous options
        for (int i = 0; i < options.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(options.get(i));
            radioGroupOptions.addView(radioButton);
        }
        radioGroupOptions.clearCheck(); // Clear any previously selected option
    }

    private boolean isCorrectAnswer(String selectedOption) {
        // Implemented the logic to check if the selected option is correct
        Question currentQuestion = questions.get(currentQuestionIndex);
        return currentQuestion.getCorrectOption().equals(selectedOption);

    }

    private List<Question> loadQuestions() {
        // Implement your logic to load quiz questions
        // This can be from a database, file, or hardcoded
        List<Question> questions = new ArrayList<>();
        List<String> question1Options = Arrays.asList("Jupiter", "Saturn", "Neptune", "Uranus");
        questions.add(new Question("What is the largest planet in our solar system?", question1Options, 0));

        List<String> question2Options = Arrays.asList("Mercury", "Mars", "Venus", "Earth");
        questions.add(new Question("Which planet is known as the Red Planet?", question2Options, 1));

        List<String> question3Options = Arrays.asList("Earth", "Venus", "Mars", "Mercury");
        questions.add(new Question("Which planet is the third closest to the sun?", question3Options, 2));

        List<String> question4Options = Arrays.asList("Neptune", "Saturn", "Jupiter", "Uranus");
        questions.add(new Question("Which planet is the furthest from the sun?", question4Options, 0));

        List<String> question5Options = Arrays.asList("Neptune", "Saturn", "Jupiter", "Uranus");
        questions.add(new Question("Which planet is the second largest in our solar system?", question5Options, 1));

        List<String> question6Options = Arrays.asList("Neptune", "Saturn", "Jupiter", "Uranus");
        questions.add(new Question("Which planet has the most moons in our solar system?", question6Options, 2));

        List<String> question7Options = Arrays.asList("Mars", "Earth", "Venus", "Mercury");
        questions.add(new Question("Which planet is the smallest in our solar system?", question7Options, 3));

        List<String> question8Options = Arrays.asList("Mars", "Earth", "Venus", "Mercury");
        questions.add(new Question("Which planet is the only one known to support life?", question8Options, 1));

        List<String> question9Options = Arrays.asList("Jupiter", "Saturn", "Uranus", "Neptune");
        questions.add(new Question("Which planet is known for its distinctive rings?", question9Options, 1));

        List<String> question10Options = Arrays.asList("Jupiter", "Saturn", "Uranus", "Neptune");
        questions.add(new Question("Which planet has the fastest rotation?", question10Options, 0));


        // Add more questions as needed
        return questions;
    }
    private void showResults() {
        // Display the quiz results
        textViewResults.setText("Quiz completed! Your score: " + score + "/" + questions.size());
    }
}
