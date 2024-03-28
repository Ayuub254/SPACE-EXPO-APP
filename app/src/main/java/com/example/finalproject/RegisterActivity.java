package com.example.finalproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextFirstname,editTextEmail, editTextPassword, editTextLastname;
    Button buttonRegister;
    FirebaseAuth mAuth;
    TextView textViewLogin;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(RegisterActivity.this, MainPage.class);
            startActivity(intent);
        }
    }
    private Map<String, Object> createQuiz(String quizName, String categoryName, int numberOfQuestions, int finalResult,String catId) {
        Map<String, Object> quizData = new HashMap<>();
        quizData.put("name", quizName);
        quizData.put("category", categoryName);
        quizData.put("no_of_questions", numberOfQuestions);
        quizData.put("final_result", finalResult);
        quizData.put("CAT_ID", catId);
        return quizData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        mAuth = FirebaseAuth.getInstance();


        editTextFirstname = findViewById(R.id.editTextFirstName);
        editTextLastname = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmailAddress2);
        editTextPassword = findViewById(R.id.editTextPassword);

        textViewLogin = findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, Login.class);
                startActivity(intent);
            }
        });

        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName, lastName, email, password;
                firstName = String.valueOf(editTextFirstname.getText());
                lastName = String.valueOf(editTextLastname.getText());
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                //check if editText fields are empty
                if(TextUtils.isEmpty(firstName)){
                    Toast.makeText(RegisterActivity.this, "Enter your first name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(lastName)){
                    Toast.makeText(RegisterActivity.this, "Enter your last name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create the user using email and password
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // If sign in is successful, store the user's first name and last name and display a message to the user.
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(firstName + " " + lastName)
                                                .build();
                                        user.updateProfile(profileUpdates);
                                    }
                                    // Add the user document to the "USERS" collection
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("Email_Id", email);
                                    userData.put("Name", firstName + " " + lastName);

                                    db.collection("USERS").document(user.getUid()).set(userData)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(RegisterActivity.this, "User document added successfully.",
                                                            Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(RegisterActivity.this, Login.class);
                                                    startActivity(intent);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(RegisterActivity.this, "Failed to add user document.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    // Create a new document for the user under the "QUIZ" collection
                                    Map<String, Object> userQuizData = new HashMap<>();
                                    userQuizData.put("quiz1", createQuiz("Quiz 1", "NEBULAS", 10, 0, "DYNDrbMhSWggYAedPUre"));
                                    userQuizData.put("quiz2", createQuiz("Quiz 2", "PLANETS", 10, 0, "lpopsOt5zsxU2ePII03y"));
                                    userQuizData.put("quiz3", createQuiz("Quiz 3", "GALAXIES", 10, 0, "uaYAOl3R2vUr6kpK9PBS"));
                                    userQuizData.put("quiz4", createQuiz("Quiz 4", "SPACE CRAFTS", 10, 0, "2PMFFEsWUofUIGpGeiWi"));


                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    String userId = currentUser.getUid();
                                    db.collection("QUIZ").document(userId)
                                            .set(userQuizData)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "User's quiz documents created successfully");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error creating user's quiz documents", e);
                                                }
                                            });

                                    Toast.makeText(RegisterActivity.this, "Authentication Success.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, Login.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}