package com.app.projetocomprova.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.projetocomprova.model.Artigos;
import com.app.projetocomprova.R;
import com.app.projetocomprova.RecuperarFullArtigo;
import com.squareup.picasso.Picasso;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ArtigoActivity extends AppCompatActivity {
    private Artigos artigo;
    private LinearLayout linearLayout, layoutInvest, layoutVerif;
    private TextView tvTerm2, tvDate2, tvTitle2, tvStatus2, tvContent2, tvVerified;
    private ImageView ivImg2, ivBgImg2;
    private ProgressBar pbLoadContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artigo);

        //referenciação
        linearLayout = findViewById(R.id.linearLayout);layoutInvest = findViewById(R.id.layoutInvest);layoutVerif = findViewById(R.id.layoutVerif);
        tvTerm2 = findViewById(R.id.tvTerm2);tvDate2 = findViewById(R.id.tvDate2);tvTitle2 = findViewById(R.id.tvTitle2);
        tvStatus2 = findViewById(R.id.tvStatus2);tvContent2 = findViewById(R.id.tvContent2); tvVerified = findViewById(R.id.tvVerified);
        ivImg2 = findViewById(R.id.ivImg2); ivBgImg2 = findViewById(R.id.ivBgImg2);
        pbLoadContent = findViewById(R.id.pbLoadContent);

        //receber objeto de ArtigosAdapter
        artigo = getIntent().getParcelableExtra("artigo");

        //actionBar
        Objects.requireNonNull(getSupportActionBar()).setTitle(artigo.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        exibicaoInicial();
        recuperarFullArtigo();

    }

    private void exibicaoInicial() {
        tvTerm2.setText(artigo.getTerm());
        tvDate2.setText(artigo.getDate());
        tvTitle2.setText(artigo.getTitle());
        tvStatus2.setText(artigo.getStatus());
        tvContent2.setText(artigo.getContent());

        Picasso.get().load(artigo.getImg()).into(ivImg2);

        if(!artigo.getStatus().equals("Comprovado")) {
            ivBgImg2.setImageResource(R.drawable.ic_red_bg);
        }else if (artigo.getStatus().equals("Sátira")) {
            ivBgImg2.setImageAlpha(0);
        }
    }

    private void recuperarFullArtigo() {
        RecuperarFullArtigo fullArtigo = new RecuperarFullArtigo(this, artigo.getLink());
        fullArtigo.execute();
    }

    public void completarArquivo(Artigos artigoContent) {
        artigo.setContentMain(artigoContent.getContentMain());
        artigo.setVerifiedContent(artigoContent.getVerifiedContent());
        artigo.setListImgInvest(artigoContent.getListImgInvest());
        artigo.setListImgVerif(artigoContent.getListImgVerif());
        exibirContentArquivo();
        setDesign();
        pbLoadContent.setVisibility(View.GONE);
    }

    private void exibirContentArquivo() {
        for(Element element : artigo.getContentMain()) {
            //Imagens
            if(element.is("img") && element.hasAttr("class")) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                Picasso.get().load(element.attr("src")).into(imageView);
                linearLayout.addView(imageView);

            //paragrafos
            }else if(element.is("p")) {
                //paragrafo com link
                if(element.getAllElements().hasAttr("href")) {
                    SpannableString ss = new SpannableString(element.text());

                    //varrer o elemento e a cada link pegar o texto(do link) e setar um span(o qual precisa do indice inicial e final do texto do link)
                    for(int i=0; i<element.getAllElements().select("a").size(); i++) {
                        final String link = element.getAllElements().select("a").get(i).attr("href");
                        final String textoLink = element.getAllElements().select("a").get(i).text();
                        int indexInicial = element.text().indexOf(textoLink);
                        int indexFinal = indexInicial + textoLink.length();

                        ClickableSpan clickableSpan = new ClickableSpan() {
                            @Override
                            public void onClick(@NonNull View widget) {
                                Intent intent = new Intent(ArtigoActivity.this, WebViewLinkActivity.class);
                                intent.putExtra("link", link);
                                intent.putExtra("texto", textoLink);
                                startActivity(intent);
                            }

                            @Override
                            public void updateDrawState(@NonNull TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(getResources().getColor(R.color.colorPrimaryDark));
                            }
                        };

                        ss.setSpan(clickableSpan, indexInicial, indexFinal, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    TextView tvParagrafoLink = new TextView(this);
                    tvParagrafoLink.setText(ss);
                    tvParagrafoLink.setMovementMethod(LinkMovementMethod.getInstance());
                    tvParagrafoLink.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tvParagrafoLink.setPadding(16, 8, 16, 8);
                    linearLayout.addView(tvParagrafoLink);

                //paragrafo sem link
                }else {
                    TextView tvParagrafo = new TextView(this);
                    tvParagrafo.setText(element.text());
                    tvParagrafo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tvParagrafo.setPadding(16, 8, 16, 8);
                    linearLayout.addView(tvParagrafo);
                }

            //titulos
            }else if(element.is("h3")) {
                TextView tvTitle = new TextView(this);
                tvTitle.setText(element.text());
                tvTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tvTitle.setPadding(16, 32, 16, 8);
                tvTitle.setTextSize(24);
                linearLayout.addView(tvTitle);

            //subtitulos
            }else if(element.is("h4")) {
                TextView tvSubTitle = new TextView(this);
                tvSubTitle.setText(element.text());
                tvSubTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tvSubTitle.setPadding(16, 8, 16, 0);
                tvSubTitle.setTextSize(18);
                linearLayout.addView(tvSubTitle);
            }
        }
    }

    private void setDesign() {
        //investigadores
        for(String img : artigo.getListImgInvest()) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams ln = new LinearLayout.LayoutParams(150, 150);
            ln.setMargins(0, 4, 16, 4);
            imageView.setLayoutParams(ln);
            imageView.setBackgroundResource(R.drawable.bg_partners);
            imageView.setElevation(5);
            Picasso.get().load(img).into(imageView);
            layoutInvest.addView(imageView);
        }

        //verificadores
        for(String img : artigo.getListImgVerif()) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams ln = new LinearLayout.LayoutParams(150, 150);
            ln.setMargins(0, 4, 16, 4);
            imageView.setLayoutParams(ln);
            imageView.setBackgroundResource(R.drawable.bg_partners);
            imageView.setElevation(5);
            Picasso.get().load(img).into(imageView);
            layoutVerif.addView(imageView);
        }

        //conteúdo verificado
        tvVerified.setText(artigo.getVerifiedContent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}