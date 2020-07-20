package com.app.projetocomprova;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.app.projetocomprova.model.Artigos;

import java.util.List;

public class NotificacaoWorker extends Worker {
    private String URL = "https://projetocomprova.com.br";

    public NotificacaoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        try {
            List<Artigos> listaDeArtigos = new RecuperarArtigos(URL).doInBackground();

            Notificacao.enviarNotificacao(getApplicationContext(), listaDeArtigos);

            return Result.success();
        }catch (Exception e) {
            return Result.failure();
        }


    }
}
