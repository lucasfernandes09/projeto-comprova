package com.app.projetocomprova;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class RecuperarFullArtigo extends AsyncTask<Void, Void, Artigos> {
    private String link;
    Artigos artigo = new Artigos();

    public RecuperarFullArtigo(String link) {
        this.link = link;
    }

    @Override
    protected Artigos doInBackground(Void... voids) {

        try {
            Document html = Jsoup.connect(link).get();
            Element verifiedContent = html.selectFirst("div.answer__content--main__container li");
            //Element contentMain = html.selectFirst("div.answer__content--main__container");
            Elements contentMain = html.select("div.answer__content--main__container");

            Log.i("infoArtigo", contentMain.size() + "");

            artigo.setTitle(contentMain.text());

        } catch (IOException e) {
            e.printStackTrace();
        }




        return artigo;
    }
}
