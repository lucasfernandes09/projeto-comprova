package com.app.projetocomprova;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.projetocomprova.activities.ArtigoActivity;
import com.app.projetocomprova.model.Artigos;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

public class RecuperarFullArtigo extends AsyncTask<Void, Void, Void> {

    private WeakReference<ArtigoActivity> artigoActivityWeakReference;
    private String link;
    private Artigos artigo = new Artigos();

    public RecuperarFullArtigo(ArtigoActivity artigoActivity, String link) {
        this.link = link;
        this.artigoActivityWeakReference = new WeakReference<>(artigoActivity);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document html = Jsoup.connect(link).get();

            List<String> listImgInvest = html.getElementsByClass("answer__credits__partners").get(0).getAllElements().eachAttr("src");
            List<String> listImgVerif = html.getElementsByClass("answer__credits answer__credits--verified").get(0).getAllElements().eachAttr("src");
            Elements contentMain = html.select("div.answer__content--main__container").get(0).getAllElements();
            Element verifiedContentHtml = html.selectFirst("div.answer__content--main__container li");

            artigo.setListImgInvest(listImgInvest);
            artigo.setListImgVerif(listImgVerif);
            artigo.setContentMain(contentMain);
            if(verifiedContentHtml == null) {
                artigo.setVerifiedContent("");
            }else {
                artigo.setVerifiedContent(verifiedContentHtml.text());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(artigoActivityWeakReference.get() != null) {
            artigoActivityWeakReference.get().completarArquivo(artigo);
        }
    }

}
