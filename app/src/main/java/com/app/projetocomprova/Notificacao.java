package com.app.projetocomprova;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.app.projetocomprova.activities.MainActivity;
import com.app.projetocomprova.activities.SplashScreenActivity;
import com.app.projetocomprova.model.Artigos;

import java.util.List;

public class Notificacao {

    private static int NOTIFICATION_ID = 0;
    private static String CHANNEL_ID = "notification";

    public Notificacao() {
    }

    public static void enviarNotificacao(Context context, List<Artigos> listaDeArtigos) {
        Artigos artigo1 = listaDeArtigos.get(0);
        Artigos artigo2 = listaDeArtigos.get(1);
        Artigos artigo3 = listaDeArtigos.get(2);

        Intent intent = new Intent(context, SplashScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_notification)
                .setContentTitle("Últimas Notícias")
                //.setContentText()
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine(artigo1.getStatus().toUpperCase() + "  " + artigo1.getTitle())
                        .addLine(artigo2.getStatus().toUpperCase() + "  " + artigo2.getTitle())
                        .addLine(artigo3.getStatus().toUpperCase() + "  " + artigo3.getTitle()))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "canal", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.enableLights(true);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, notificacao.build());
    }

}
