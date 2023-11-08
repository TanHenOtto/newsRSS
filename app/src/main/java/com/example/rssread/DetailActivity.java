package com.example.rssread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DetailActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        webView= (WebView)findViewById(R.id.webView);
        Intent intent = getIntent();
        String URL = intent.getStringExtra("link");
        webView.loadUrl(URL);
        webView.setWebViewClient(new WebViewClient());

    }
}