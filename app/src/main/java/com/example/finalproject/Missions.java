package com.example.finalproject;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Missions extends AppCompatActivity {
    private WebView myMissionsWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missions);

        myMissionsWebView = findViewById(R.id.webViewMissions);
        WebSettings webSettings = myMissionsWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String websiteUrl = "https://www.nasa.gov/missions/";
        myMissionsWebView.loadUrl(websiteUrl);
    }
    public void onBackPressed() {
        if (myMissionsWebView.canGoBack()) {
            myMissionsWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}