package com.app.projetocomprova.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.app.projetocomprova.R;
import com.app.projetocomprova.RecuperarArtigos;
import com.app.projetocomprova.model.Artigos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SplashScreenActivity extends AppCompatActivity {

    private String URL = "https://projetocomprova.com.br/page/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new RecuperarArtigos(this, URL + 1).execute();

    }
}