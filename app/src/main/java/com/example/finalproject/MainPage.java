package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainPage extends AppCompatActivity{

    DrawerLayout drawerLayout;
    ImageButton buttonDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        drawerLayout = findViewById(R.id.drawerLayout);
        buttonDrawerToggle = findViewById(R.id.buttonDrawerToggle);
        navigationView = findViewById(R.id.navigationView);

        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && currentUser.getEmail().equals("ayubhussein@gmail.com")) {
            showAdminSection();
        }



        View headerView = navigationView.getHeaderView(0);
        ImageView useImage = headerView.findViewById(R.id.userImage);
        TextView textUserEmail = headerView.findViewById(R.id.headerUserEmail);
        TextView textUserName = headerView.findViewById(R.id.headerUserName);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference userRef = db.collection("USERS").document(user.getUid());
            userRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String userName = documentSnapshot.getString("Name");
                    String userEmail = documentSnapshot.getString("Email_Id");
                    textUserName.setText(userName);
                    textUserEmail.setText(userEmail);
                } else {
                    // User document does not exist
                    textUserName.setText(""); // Set an empty name or handle it as needed
                    textUserEmail.setText(""); // Set an empty email or handle it as needed
                }
            }).addOnFailureListener(e -> {
                // Handle failure to fetch user document
                textUserName.setText(""); // Set an empty name or handle it as needed
                textUserEmail.setText(""); // Set an empty email or handle it as needed
            });
        } else {
            // User is not signed in
            textUserName.setText(""); // Set an empty name or handle it as needed
            textUserEmail.setText(""); // Set an empty email or handle it as needed
        }


        useImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainPage.this, textUserEmail.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        if (savedInstanceState == null) {
            //This code block opens the home fragment immediately
            Toast.makeText(MainPage.this, "Home", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

            navigationView.setCheckedItem(R.id.nav_home);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    Toast.makeText(MainPage.this, "Home", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                }
                else if (itemId == R.id.nav_documentaries) {
                    Toast.makeText(MainPage.this, "Quiz", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DocumentariesFragment()).commit();
                }
                else if (itemId == R.id.nav_fav) {
                    Toast.makeText(MainPage.this, "Nasa", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavouritesFragment()).commit();
                }
                else if(itemId == R.id.nav_admin){
                    // Check if the current user is an admin
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser != null && currentUser.getEmail().equals("ayubhussein@gmail.com")) {
                        // User is an admin, handle admin section
                        handleAdminSection();
                    } else {
                        // Regular user, do nothing or show a message
                        Toast.makeText(MainPage.this, "You are not authorized to access this section", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (itemId == R.id.nav_logout) {
                    // Logout the user
                    FirebaseAuth.getInstance().signOut();
                    // Redirect to the login activity
                    Intent intent = new Intent(MainPage.this, Login.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }

            private void handleAdminSection() {
                Toast.makeText(MainPage.this, "Admin", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminFragment()).commit();
            }

        });

    }

    public void showAdminSection() {
        NavigationView navigationView = findViewById(R.id.navigationView);
        Menu navMenu = navigationView.getMenu();
        navMenu.findItem(R.id.nav_admin).setVisible(true);
    }
}

