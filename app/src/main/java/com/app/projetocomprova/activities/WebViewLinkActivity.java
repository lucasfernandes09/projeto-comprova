package com.app.projetocomprova.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.app.projetocomprova.R;

import java.util.Objects;

public class WebViewLinkActivity extends AppCompatActivity {
    private WebView wvLink;
    private ProgressBar pbWebView;
    private String link, textoLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_link);

        //receber link de ArtigoActivity
        link = getIntent().getStringExtra("link");
        textoLink = getIntent().getStringExtra("texto");

        //toolbar
        Toolbar toolbarWebView = findViewById(R.id.toolbarWebView);
        toolbarWebView.setTitle(textoLink);
        setSupportActionBar(toolbarWebView);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //ref
        wvLink = findViewById(R.id.wvLink);
        pbWebView = findViewById(R.id.pbWebView);

        iniciarWebView();

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void iniciarWebView() {
        wvLink.setWebChromeClient(new MyWebChromeClient());
        wvLink.getSettings().setJavaScriptEnabled(true);
        wvLink.loadUrl(link);
    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            pbWebView.setProgress(newProgress);
            if(pbWebView.getProgress() == 100) {
                pbWebView.setVisibility(View.GONE);
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