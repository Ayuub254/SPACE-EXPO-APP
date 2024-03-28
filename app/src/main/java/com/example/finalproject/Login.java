package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText editTextEmail2, editTextPassword2;
    Button buttonLogin;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(Login.this, MainPage.class);
            startActivity(intent);
        }
    }

    private void handleAdminLogin(FirebaseUser user) {
        if (user != null && user.getEmail().equals("ayubhussein@gmail.com")) {
            // Admin login successful
            Intent intent = new Intent(Login.this, MainPage.class);
            intent.putExtra("isAdmin", true); // Pass admin flag to MainPage
            startActivity(intent);
            Toast.makeText(Login.this, "Admin Login Successful.", Toast.LENGTH_SHORT).show();
        } else {
            // Regular user login successful
            Intent intent = new Intent(Login.this, MainPage.class);
            intent.putExtra("isAdmin", false); // Pass admin flag to MainPage
            startActivity(intent);
            Toast.makeText(Login.this, "Login Successful.", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        editTextEmail2 = findViewById(R.id.editTextEmailAddress2);
        editTextPassword2 = findViewById(R.id.editTextPassword2);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(editTextEmail2.getText());
                password = String.valueOf(editTextPassword2.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    handleAdminLogin(user);
                                }
                                    else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Login Failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        TextView editTextCreateAccount = findViewById(R.id.textViewCreateAccount);
        editTextCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}