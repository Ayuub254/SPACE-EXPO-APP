package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;

public class StarsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stars);

        WebView webView = findViewById(R.id.webViewStars);
        String starsVideo = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/EFO_bsg1sw8?si=ZzqdV95xV5HiZqQ5\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webView.loadData(starsVideo,"text/html", "utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        ImageButton backButton = findViewById(R.id.imageButtonBackStars);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // This will simulate a back button press
            }
        });

        ImageButton quizStars = findViewById(R.id.imageButtonQuizStars);
        quizStars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StarsActivity.this, NebulasQuiz.class);
                startActivity(intent);
            }
        });
    }
}