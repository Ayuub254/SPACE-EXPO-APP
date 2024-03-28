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


public class GalaxyQuiz extends AppCompatActivity {
    private TextView textViewQuestion;
    private RadioGroup radioGroupOptions;
    private Button buttonNext;
    private TextView textViewResults;

    private int currentQuestionIndex1 = 0;
    private int score1 = 0;
    private int totalQuestions1 = 0;
    private List<Question> questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galaxy_quiz);
        // Initialize views
        textViewQuestion = findViewById(R.id.textViewQuestion2);
        radioGroupOptions = findViewById(R.id.radioGroupOptionsGalaxies);
        buttonNext = findViewById(R.id.buttonNextGalaxies);
        textViewResults = findViewById(R.id.textViewResultsGalaxies);

        Button buttonBack = findViewById(R.id.buttonBackGalaxies);
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
                    Toast.makeText(GalaxyQuiz.this, "Please select an option", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get the selected option
                RadioButton selectedRadioButton = findViewById(selectedOptionId);
                String selectedOption = selectedRadioButton.getText().toString();

                // Check if the selected option is correct
                if (isCorrectAnswer(selectedOption)) {
                    // Increment score or handle correct answer logic
                    Toast.makeText(GalaxyQuiz.this, "Correct!", Toast.LENGTH_SHORT).show();
                    score1++;
                } else {
                    Toast.makeText(GalaxyQuiz.this, "Incorrect!", Toast.LENGTH_SHORT).show();

                }

                // Move to the next question
                currentQuestionIndex1++;
                if (currentQuestionIndex1 < questions.size()) {
                    showQuestion();
                } else {
                    // Quiz completed, show result or navigate to result activity
                    showResults();
                    Toast.makeText(GalaxyQuiz.this, "Quiz completed!", Toast.LENGTH_SHORT).show();
                    // Update the user's final score in Firestore
                    updateFinalScoreInFirestore(score1);
                }

            }

            private void updateFinalScoreInFirestore(int score) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> scoreData = new HashMap<>();
                scoreData.put("FINAL_RESULT", score); // Assuming 'score' is the user's final score
                db.collection("QUIZ").document("uaYAOl3R2vUr6kpK9PBS").update(scoreData)
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
        Question currentQuestion = questions.get(currentQuestionIndex1);
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
        Question currentQuestion = questions.get(currentQuestionIndex1);
        return currentQuestion.getCorrectOption().equals(selectedOption);

    }

    private List<Question> loadQuestions() {
        // Implement your logic to load quiz questions
        // This can be from a database, file, or hardcoded
        List<Question> questions = new ArrayList<>();
        List<String> question1Options = Arrays.asList("Elliptical", "Spiral", "Irregular", "Lenticular");
        questions.add(new Question("What type of galaxy is the Milky Way?", question1Options, 1));

        List<String> question2Options = Arrays.asList("Andromeda", "Sagittarius", "Triangulum", "Canis Major Dwarf");
        questions.add(new Question("Which galaxy is the closest to the Milky Way?", question2Options, 0));

        List<String> question3Options = Arrays.asList("Pinwheel Galaxy", "Black Eye Galaxy", "Whirlpool Galaxy", "Sombrero Galaxy");
        questions.add(new Question("Which galaxy is also known as M51?", question3Options, 0));

        List<String> question4Options = Arrays.asList("Hubble Space Telescope", "James Webb Space Telescope", "Spitzer Space Telescope", "Chandra X-ray Observatory");
        questions.add(new Question("Which telescope discovered the first evidence of galaxies beyond the Milky Way?", question4Options, 0));

        List<String> question5Options = Arrays.asList("2 million", "100 billion", "1 trillion", "More than 2 trillion");
        questions.add(new Question("How many galaxies are estimated to exist in the observable universe?", question5Options, 3));

        List<String> question6Options = Arrays.asList("Antennae Galaxies", "Cartwheel Galaxy", "Sombrero Galaxy", "Black Eye Galaxy");
        questions.add(new Question("Which galaxy collision is known for its spectacular ring-like structure?", question6Options, 0));

        List<String> question7Options = Arrays.asList("Markarian's Chain", "Virgo Cluster", "Fornax Cluster", "Coma Cluster");
        questions.add(new Question("Which galaxy cluster is the closest to the Local Group of galaxies?", question7Options, 1));

        List<String> question8Options = Arrays.asList("IC 1101", "Andromeda Galaxy", "Milky Way", "Triangulum Galaxy");
        questions.add(new Question("Which is the largest known galaxy by volume?", question8Options, 0));

        List<String> question9Options = Arrays.asList("Stephen Hawking", "Edwin Hubble", "Carl Sagan", "Galileo Galilei");
        questions.add(new Question("Which scientist first proposed that some nebulae were actually distant galaxies?", question9Options, 1));

        List<String> question10Options = Arrays.asList("Starburst Galaxy", "Irregular Galaxy", "Lenticular Galaxy", "Elliptical Galaxy");
        questions.add(new Question("What type of galaxy is M82, also known as the Cigar Galaxy?", question10Options, 0));



        // Add more questions as needed
        return questions;
    }
    private void showResults() {
        // Display the quiz results
        textViewResults.setText("Quiz completed! Your score: " + score1 + "/" + questions.size());
    }
}


