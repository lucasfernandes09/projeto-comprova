package com.app.projetocomprova;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class CarregarImg extends AsyncTask<Void, Void, Void> {

    ImageView imageView;
    String imgUrl;

    public CarregarImg(ImageView imageView, String imgUrl) {
        this.imageView = imageView;
        this.imgUrl = imgUrl;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Picasso.get().load(imgUrl).into(imageView);
        return null;
    }
}
