package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;

public class BlackHolesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_holes);

        WebView webView = findViewById(R.id.webViewBlackHoles);
        String blackHolesVideo = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/qp2cqDkAU-0?si=nL76FMhC5mi39khb\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webView.loadData(blackHolesVideo,"text/html", "utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        ImageButton backButton = findViewById(R.id.imageButtonBackBlackHoles);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // This will simulate a back button press
            }
        });

        ImageButton quizBlackHoles = findViewById(R.id.imageButtonQuizBlackHoles);
        quizBlackHoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BlackHolesActivity.this, PlanetQuiz.class);
                startActivity(intent);
            }
        });
    }
}