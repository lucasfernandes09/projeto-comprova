package com.app.projetocomprova;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.app.projetocomprova.activities.MainActivity;
import com.app.projetocomprova.activities.SplashScreenActivity;
import com.app.projetocomprova.fragments.HomeFragment;
import com.app.projetocomprova.model.Artigos;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

public class RecuperarArtigos extends AsyncTask<Void, Void, List<Artigos>> {

    private List<Artigos> listaDeArtigos = new ArrayList<>();
    private WeakReference<HomeFragment> homeFragmentWeakReference;
    private WeakReference<SplashScreenActivity> splashScreenActivityWeakReference;
    private String url;
    private Boolean splashScreen;

    public RecuperarArtigos(HomeFragment homeFragment, String url, List<Artigos> listaDeArtigos) {
        this.homeFragmentWeakReference = new WeakReference<>(homeFragment);
        this.url = url;
        this.listaDeArtigos = listaDeArtigos;
        this.splashScreen = false;
    }

    //construtor da SplashScreen
    public RecuperarArtigos(SplashScreenActivity splashScreenActivity ,String url) {
        this.splashScreenActivityWeakReference = new WeakReference<>(splashScreenActivity);
        this.url = url;
        this.splashScreen = true;
    }

    @Override
    protected List<Artigos> doInBackground(Void... voids) {
        try {
            Document html = Jsoup.connect(url).get();
            Elements terms = html.select("h3.answer__term");
            Elements dates = html.select("span.answer__credits__date");
            Elements titles = html.select("a.answer__title__link");
            List<String> listaDeImgs = html.select("div.answer__image").eachAttr("style");
            Elements contents = html.select("section.answer__content");
            Elements sharing = html.getElementsByClass("answer__content answer__content__sm-link__container");

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
                artigo.setShareFacebook(sharing.get(i).select("a").get(0).attr("href"));
                artigo.setShareTwitter(sharing.get(i).select("a").get(1).attr("href"));

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
        if (splashScreen) {
            if(splashScreenActivityWeakReference.get() != null) {
                Intent intent = new Intent(splashScreenActivityWeakReference.get(), MainActivity.class);
                intent.putParcelableArrayListExtra("artigos", (ArrayList<Artigos>) listaDeArtigos);
                splashScreenActivityWeakReference.get().startActivity(intent);
            }
        }else {
            if(homeFragmentWeakReference.get() != null) {
                homeFragmentWeakReference.get().exibirArtigos(this.listaDeArtigos);
            }
        }
    }

    public String configData(String data) {
        String dataFormatada = data.substring(8,10) + "-";
        dataFormatada += data.substring(5,7) + "-";
        dataFormatada += data.substring(0,4);

        return dataFormatada;
    }
}
