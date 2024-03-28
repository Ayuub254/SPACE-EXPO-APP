package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FavouritesFragment extends Fragment {
    private WebView mywebView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        mywebView = (WebView) view.findViewById(R.id.WebViewSpaceContent);
        mywebView.getSettings().setJavaScriptEnabled(true);
        mywebView.setWebViewClient(new WebViewClient());
        mywebView.loadUrl("https://www.nasa.gov/");
        WebSettings webSettings = mywebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        return view;
    }
}
