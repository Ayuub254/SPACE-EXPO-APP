package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.FavouritesFragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;

public class NebulasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nebulas);

        WebView webView = findViewById(R.id.webViewNebulas);
        String nebulaeVideo = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/pTzVCcFHChU?si=WLrq8iaRARdADoes\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webView.loadData(nebulaeVideo, "text/html", "utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        ImageButton backButton = findViewById(R.id.imageButtonBackNebula);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // This will simulate a back button press
            }
        });

        ImageButton quizNebula = findViewById(R.id.imageButtonquizNebula);
        quizNebula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NebulasActivity.this, NebulasQuiz.class);
                startActivity(intent);
            }
        });
    }
}
