package com.example.finalproject;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SolarSystem extends AppCompatActivity {
    private WebView mySpaceWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solar_system);

        mySpaceWebView = findViewById(R.id.webViewSolarSystem);
        WebSettings webSettings = mySpaceWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String websiteUrl = "https://science.nasa.gov/solar-system/";
        mySpaceWebView.loadUrl(websiteUrl);
    }
    public void onBackPressed() {
        if (mySpaceWebView.canGoBack()) {
            mySpaceWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}