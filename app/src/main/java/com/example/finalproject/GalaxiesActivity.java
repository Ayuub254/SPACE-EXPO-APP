package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;

public class GalaxiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galaxies);

        WebView webView = findViewById(R.id.webViewGalaxies);
        String galaxiesVideo = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/rkz34RuTYtg?si=70324yhvTN6-DNY8\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webView.loadData(galaxiesVideo,"text/html", "utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        ImageButton backButton = findViewById(R.id.imageButtonBackGalaxies);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // This will simulate a back button press
            }
        });

        ImageButton quizGalaxies = findViewById(R.id.imageButtonQuizGalaxies);
        quizGalaxies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GalaxiesActivity.this, GalaxyQuiz.class);
                startActivity(intent);
            }
        });
    }
}