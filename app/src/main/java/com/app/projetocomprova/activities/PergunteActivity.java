package com.app.projetocomprova.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.app.projetocomprova.R;

import java.util.Objects;

public class PergunteActivity extends AppCompatActivity {
    private ProgressBar pbPergunte;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pergunte);

        //toolbar
        Toolbar toolbarPergunte = findViewById(R.id.toolbarPergunte);
        toolbarPergunte.setElevation(0);
        setSupportActionBar(toolbarPergunte);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        pbPergunte = findViewById(R.id.pbPergunte);
        webView = findViewById(R.id.webView);

        iniciarWebView();

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void iniciarWebView() {
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://projetocomprova.com.br/pergunte-ao-comprova-2/");
    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            pbPergunte.setProgress(newProgress);
            if(pbPergunte.getProgress() == 100) {
                pbPergunte.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}