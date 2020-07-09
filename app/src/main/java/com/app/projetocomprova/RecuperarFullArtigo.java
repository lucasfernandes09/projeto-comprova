package com.app.projetocomprova;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class RecuperarFullArtigo extends AsyncTask<Void, Void, Artigos> {
    private String link;
    private Artigos artigo = new Artigos();

    public RecuperarFullArtigo(String link) {
        this.link = link;
    }

    @Override
    protected Artigos doInBackground(Void... voids) {
        try {
            Document html = Jsoup.connect(link).get();

            List<String> listImgInvest = html.getElementsByClass("answer__credits__partners").get(0).getAllElements().eachAttr("src");
            List<String> listImgVerif = html.getElementsByClass("answer__credits answer__credits--verified").get(0).getAllElements().eachAttr("src");
            Element verifiedContent = html.selectFirst("div.answer__content--main__container li");
            Elements contentMain = html.select("div.answer__content--main__container").get(0).getAllElements();

            artigo.setListImgInvest(listImgInvest);
            artigo.setListImgVerif(listImgVerif);
            artigo.setVerifiedContent(verifiedContent);
            artigo.setContentMain(contentMain);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return artigo;
    }
}
