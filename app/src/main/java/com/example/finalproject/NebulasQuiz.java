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

public class NebulasQuiz extends AppCompatActivity {
    private TextView textViewQuestion;
    private RadioGroup radioGroupOptions;
    private Button buttonNext;
    private TextView textViewResults;

    private int currentQuestionIndex2 = 0;
    private int score2 = 0;
    private int totalQuestions2 = 0;
    private List<Question> questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nebulas_quiz);

        textViewQuestion = findViewById(R.id.textViewQuestion3);
        radioGroupOptions = findViewById(R.id.radioGroupOptionsNebulae);
        buttonNext = findViewById(R.id.buttonNextNebulae);
        textViewResults = findViewById(R.id.textViewResultsNebulae);

        Button buttonBack = findViewById(R.id.buttonBackNebulae);
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
                    Toast.makeText(NebulasQuiz.this, "Please select an option", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get the selected option
                RadioButton selectedRadioButton = findViewById(selectedOptionId);
                String selectedOption = selectedRadioButton.getText().toString();

                // Check if the selected option is correct
                if (isCorrectAnswer(selectedOption)) {
                    // Increment score or handle correct answer logic
                    Toast.makeText(NebulasQuiz.this, "Correct!", Toast.LENGTH_SHORT).show();
                    score2++;
                } else {
                    Toast.makeText(NebulasQuiz.this, "Incorrect!", Toast.LENGTH_SHORT).show();

                }

                // Move to the next question
                currentQuestionIndex2++;
                if (currentQuestionIndex2 < questions.size()) {
                    showQuestion();
                } else {
                    // Quiz completed, show result or navigate to result activity
                    showResults();
                    Toast.makeText(NebulasQuiz.this, "Quiz completed!", Toast.LENGTH_SHORT).show();
                    // Update the user's final score in Firestore
                    updateFinalScoreInFirestore(score2);
                }

            }

            private void updateFinalScoreInFirestore(int score) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> scoreData = new HashMap<>();
                scoreData.put("FINAL_RESULT", score);
                db.collection("QUIZ").document("DYNDrbMhSWggYAedPUre").update(scoreData)
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
        Question currentQuestion = questions.get(currentQuestionIndex2);
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
        Question currentQuestion = questions.get(currentQuestionIndex2);
        return currentQuestion.getCorrectOption().equals(selectedOption);

    }

    private List<Question> loadQuestions() {
        // Implement your logic to load quiz questions
        // This can be from a database, file, or hardcoded
        List<Question> questions = new ArrayList<>();
        // Question 1
        List<String> question1Options = Arrays.asList("Carina Nebula", "Veil Nebula", "Crab Nebula", "Lagoon Nebula");
        questions.add(new Question("What is the name of the nebula that is the remnants of a supernova observed by Chinese astronomers in 1054 AD?", question1Options, 2));

        // Question 2
        List<String> question2Options = Arrays.asList("Orion Nebula", "Crab Nebula", "Helix Nebula", "Eagle Nebula");
        questions.add(new Question("Which nebula is known for its distinctive red, green, and blue colors, often referred to as the 'Pillars of Creation'?", question2Options, 3));

        // Question 3
        List<String> question3Options = Arrays.asList("Tarantula Nebula", "Orion Nebula", "Rosette Nebula", "Eagle Nebula");
        questions.add(new Question("What is the name of the largest and most massive star-forming region in the Local Group of galaxies, containing several hundred thousand stars?", question3Options, 0));

        // Question 4
        List<String> question4Options = Arrays.asList("Helix Nebula", "Crab Nebula", "Ring Nebula", "Eskimo Nebula");
        questions.add(new Question("Which nebula is known as the 'Eye of God' due to its resemblance to a human eye?", question4Options, 0));

        // Question 5
        List<String> question5Options = Arrays.asList("Horsehead Nebula", "Pipe Nebula", "Coalsack Nebula", "Barnard 68");
        questions.add(new Question("What is the name of the dark nebula located in the constellation of Orion, which is visible to the naked eye as a dark patch against the bright background of the Milky Way?", question5Options, 2));

        // Question 6
        List<String> question6Options = Arrays.asList("Veil Nebula", "Crab Nebula", "Cygnus Loop", "Rosette Nebula");
        questions.add(new Question("Which nebula is a supernova remnant located in the constellation of Taurus and is known for its filamentary structure?", question6Options, 1));

        // Question 7
        List<String> question7Options = Arrays.asList("Helix Nebula", "Eskimo Nebula", "Cat's Eye Nebula", "Butterfly Nebula");
        questions.add(new Question("What is the name of the planetary nebula in the constellation of Aquarius, which is known for its bipolar shape?", question7Options, 2));

        // Question 8
        List<String> question8Options = Arrays.asList("Horsehead Nebula", "Rosette Nebula", "Flame Nebula", "Orion Nebula");
        questions.add(new Question("Which nebula is a diffuse nebula in the constellation of Orion, surrounding the multiple star system Theta Orionis?", question8Options, 3));

        // Question 9
        List<String> question9Options = Arrays.asList("Crab Nebula", "Veil Nebula", "Cygnus Loop", "Rosette Nebula");
        questions.add(new Question("What is the name of the supernova remnant in the constellation of Cygnus, which is also known as the Veil Nebula?", question9Options, 2));

        // Question 10
        List<String> question10Options = Arrays.asList("Rosette Nebula", "Trifid Nebula", "Lagoon Nebula", "Eagle Nebula");
        questions.add(new Question("Which nebula is a bright emission nebula in the constellation of Sagittarius, also known as the 'Lagoon Nebula'?", question10Options, 2));

        return questions;
    }
    private void showResults() {
        // Display the quiz results
        textViewResults.setText("Quiz completed! Your score: " + score2 + "/" + questions.size());
    }

    }