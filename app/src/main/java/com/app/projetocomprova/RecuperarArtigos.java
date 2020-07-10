package com.app.projetocomprova;

import android.os.AsyncTask;
import android.util.Log;

import com.app.projetocomprova.fragments.HomeFragment;
import com.app.projetocomprova.model.Artigos;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class RecuperarArtigos extends AsyncTask<Void, Void, Void> {

    private List<Artigos> listaDeArtigos = new ArrayList<>();
    private WeakReference<HomeFragment> homeFragmentWeakReference;
    private String url;

    public RecuperarArtigos(HomeFragment homeFragment, String url, List<Artigos> listaDeArtigos) {
        this.homeFragmentWeakReference = new WeakReference<>(homeFragment);
        this.url = url;
        this.listaDeArtigos = listaDeArtigos;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document html = Jsoup.connect(url).get();
            Elements terms = html.select("h3.answer__term");
            Elements dates = html.select("span.answer__credits__date");
            Elements titles = html.select("a.answer__title__link");
            List<String> listaDeImgs = html.select("div.answer__image").eachAttr("style");
            Elements contents = html.select("section.answer__content");

            for(int i=0; i<terms.size(); i++) {
                Artigos artigo = new Artigos();
                artigo.setTerm(terms.get(i).text());
                artigo.setDate(configData(dates.get(i).text()));
                artigo.setTitle(titles.get(i).text());
                artigo.setLink(titles.get(i).attr("href"));
                artigo.setImg(listaDeImgs.get(i).replace("background-image: url( ", "")
                        .replace(" );", "").replace("\"", ""));
                artigo.setContent(contents.select("dd.answer__tag__details").get(i).text());
                artigo.setStatus(contents.select("dt.answer__tag").get(i).text());

                listaDeArtigos.add(artigo);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(homeFragmentWeakReference.get() != null) {
            homeFragmentWeakReference.get().exibirArtigos(listaDeArtigos);
        }
    }

    public String configData(String data) {
        String dataFormatada = data.substring(8,10) + "-";
        dataFormatada += data.substring(5,7) + "-";
        dataFormatada += data.substring(0,4);

        return dataFormatada;
    }
}
