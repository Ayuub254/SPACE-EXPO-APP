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

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceCraftsQuiz extends AppCompatActivity {
    private TextView textViewQuestion;
    private RadioGroup radioGroupOptions;
    private Button buttonNext;
    private TextView textViewResults;

    private int currentQuestionIndex3 = 0;
    private int score3 = 0;
    private int totalQuestions3 = 0;
    private List<Question> questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_crafts_quiz);

        textViewQuestion = findViewById(R.id.textViewQuestion4);
        radioGroupOptions = findViewById(R.id.radioGroupOptionsSpaceCrafts);
        buttonNext = findViewById(R.id.buttonNextSpaceCrafts);
        textViewResults = findViewById(R.id.textViewResultsSpaceCrafts);

        Button buttonBack = findViewById(R.id.buttonBackSpaceCrafts);
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
                    Toast.makeText(SpaceCraftsQuiz.this, "Please select an option", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get the selected option
                RadioButton selectedRadioButton = findViewById(selectedOptionId);
                String selectedOption = selectedRadioButton.getText().toString();

                // Check if the selected option is correct
                if (isCorrectAnswer(selectedOption)) {
                    // Increment score or handle correct answer logic
                    Toast.makeText(SpaceCraftsQuiz.this, "Correct!", Toast.LENGTH_SHORT).show();
                    score3++;
                } else {
                    Toast.makeText(SpaceCraftsQuiz.this, "Incorrect!", Toast.LENGTH_SHORT).show();

                }

                // Move to the next question
                currentQuestionIndex3++;
                if (currentQuestionIndex3 < questions.size()) {
                    showQuestion();
                } else {
                    // Quiz completed, show result or navigate to result activity
                    showResults();
                    Toast.makeText(SpaceCraftsQuiz.this, "Quiz completed!", Toast.LENGTH_SHORT).show();
                    // Update the user's final score in Firestore
                    updateFinalScoreInFirestore(score3);
                }

            }

            private void updateFinalScoreInFirestore(int score) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> scoreData = new HashMap<>();
                scoreData.put("FINAL_RESULT", score); // Assuming 'score' is the user's final score
                db.collection("QUIZ").document("2PMFFEsWUofUIGpGeiWi").update(scoreData)
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
        Question currentQuestion = questions.get(currentQuestionIndex3);
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
        Question currentQuestion = questions.get(currentQuestionIndex3);
        return currentQuestion.getCorrectOption().equals(selectedOption);

    }

    private List<Question> loadQuestions() {
        // Implement your logic to load quiz questions
        // This can be from a database, file, or hardcoded
        List<Question> questions = new ArrayList<>();
        // Question 1
        List<String> question1Options = Arrays.asList("Hubble Space Telescope", "James Webb Space Telescope", "Spitzer Space Telescope", "Chandra X-ray Observatory");
        questions.add(new Question("Which telescope is famous for its stunning images of the cosmos?", question1Options, 0));

        List<String> question2Options = Arrays.asList("Apollo 11", "Voyager 1", "SpaceX Dragon", "ISS");
        questions.add(new Question("Which spacecraft was the first to land humans on the Moon?", question2Options, 0));

        List<String> question3Options = Arrays.asList("Neil Armstrong", "Yuri Gagarin", "Buzz Aldrin", "Alan Shepard");
        questions.add(new Question("Who was the first human to journey into outer space?", question3Options, 1));

        List<String> question4Options = Arrays.asList("Sputnik 1", "Explorer 1", "Vostok 1", "Mercury-Redstone 3");
        questions.add(new Question("Which was the first artificial Earth satellite launched by the Soviet Union in 1957?", question4Options, 0));

        List<String> question5Options = Arrays.asList("Cassini-Huygens", "Voyager 1", "New Horizons", "Mars Rover Curiosity");
        questions.add(new Question("Which spacecraft provided close-up images of Saturn, its rings, and its moons?", question5Options, 0));

        List<String> question6Options = Arrays.asList("Valentina Tereshkova", "Sally Ride", "Kalpana Chawla", "Mae Jemison");
        questions.add(new Question("Who was the first woman to travel to space?", question6Options, 0));

        List<String> question7Options = Arrays.asList("Gemini", "Apollo", "Mercury", "Shuttle");
        questions.add(new Question("Which NASA program was responsible for the first American manned spaceflights?", question7Options, 2));

        List<String> question8Options = Arrays.asList("Soyuz", "SpaceX Dragon", "Apollo", "Space Shuttle");
        questions.add(new Question("Which spacecraft has been used by Russia to transport cosmonauts to and from the ISS?", question8Options, 0));

        List<String> question9Options = Arrays.asList("Hubble Space Telescope", "Chandra X-ray Observatory", "Spitzer Space Telescope", "James Webb Space Telescope");
        questions.add(new Question("Which telescope is designed to be the successor to the Hubble Space Telescope?", question9Options, 3));

        List<String> question10Options = Arrays.asList("Neil Armstrong", "Buzz Aldrin", "Michael Collins", "Yuri Gagarin");
        questions.add(new Question("Who was the first person to walk on the Moon?", question10Options, 0));

        return questions;
    }
    private void showResults() {
        // Display the quiz results
        textViewResults.setText("Quiz completed! Your score: " + score3 + "/" + questions.size());
    }

    }