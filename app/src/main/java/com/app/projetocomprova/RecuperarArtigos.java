package com.app.projetocomprova;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecuperarArtigos extends AsyncTask<Void, Void, List<Artigos>> {
    private List<Artigos> listaDeArtigos = new ArrayList<>();

    @Override
    protected List<Artigos> doInBackground(Void... voids) {

        try {
            Document html = Jsoup.connect("https://projetocomprova.com.br/").get();
            Elements terms = html.select("h3.answer__term");
            Elements titles = html.select("a.answer__title__link");
            Elements imgs = html.select("div.answer__image");
            Elements contents = html.select("section.answer__content");

            for(int i=0; i<terms.size(); i++) {
                Artigos artigo = new Artigos();
                artigo.setTerm(terms.get(i).text());
                artigo.setTitle(titles.get(i).text());
                artigo.setImg(imgs.get(i).attr("style").replace("background-image: url( ", "")
                        .replace(" );", ""));
                artigo.setContent(contents.select("dd.answer__tag__details").get(i).text());
                artigo.setStatus(contents.select("dt.answer__tag").get(i).text());

                listaDeArtigos.add(artigo);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listaDeArtigos;
    }

    @Override
    protected void onPostExecute(List<Artigos> listaDeArtigos) {
        super.onPostExecute(listaDeArtigos);

    }
}
