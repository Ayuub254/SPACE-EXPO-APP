package com.example.finalproject;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;


public class SpaceCrafts extends AppCompatActivity {
    private WebView mySpaceCraftsWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_crafts);

        mySpaceCraftsWebView = findViewById(R.id.webViewSpaceCrafts);
        WebSettings webSettings = mySpaceCraftsWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String websiteUrl = "https://www.nasa.gov/humans-in-space/spaceships-and-rockets/";
        mySpaceCraftsWebView.loadUrl(websiteUrl);
    }

    public void onBackPressed() {
        if (mySpaceCraftsWebView.canGoBack()) {
            mySpaceCraftsWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}