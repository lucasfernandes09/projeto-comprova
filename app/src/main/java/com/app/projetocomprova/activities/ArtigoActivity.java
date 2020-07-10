package com.app.projetocomprova.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.projetocomprova.model.Artigos;
import com.app.projetocomprova.R;
import com.app.projetocomprova.RecuperarFullArtigo;
import com.squareup.picasso.Picasso;

import org.jsoup.nodes.Element;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ArtigoActivity extends AppCompatActivity {
    private Artigos artigo;
    private LinearLayout linearLayout, layoutInvest, layoutVerif;
    private TextView tvTerm2, tvDate2, tvTitle2, tvStatus2, tvContent2, tvVerified;
    private ImageView ivImg2, ivBgImg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artigo);

        //referenciação
        linearLayout = findViewById(R.id.linearLayout);layoutInvest = findViewById(R.id.layoutInvest);layoutVerif = findViewById(R.id.layoutVerif);
        tvTerm2 = findViewById(R.id.tvTerm2);tvDate2 = findViewById(R.id.tvDate2);tvTitle2 = findViewById(R.id.tvTitle2);
        tvStatus2 = findViewById(R.id.tvStatus2);tvContent2 = findViewById(R.id.tvContent2); tvVerified = findViewById(R.id.tvVerified);
        ivImg2 = findViewById(R.id.ivImg2); ivBgImg2 = findViewById(R.id.ivBgImg2);

        //receber objeto de ArtigosAdapter
        artigo = getIntent().getParcelableExtra("artigo");

        //actionBar
        Objects.requireNonNull(getSupportActionBar()).setTitle(artigo.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recuperarFullArtigo();

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
    }

    private void exibirContentArquivo() {
        for(Element element : artigo.getContentMain()) {
            if(element.is("img") && element.hasAttr("class")) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                Picasso.get().load(element.attr("src")).into(imageView);
                linearLayout.addView(imageView);

            }else if(element.is("p")) {
                TextView tvParagrafo = new TextView(this);
                tvParagrafo.setText(element.text());
                tvParagrafo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tvParagrafo.setPadding(16, 8, 16, 8);
                linearLayout.addView(tvParagrafo);
            }
            else if(element.is("h3")) {
                TextView tvTitle = new TextView(this);
                tvTitle.setText(element.text());
                tvTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tvTitle.setPadding(16, 32, 16, 8);
                tvTitle.setTextSize(24);
                linearLayout.addView(tvTitle);

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

        tvTerm2.setText(artigo.getTerm());
        tvDate2.setText(artigo.getDate());
        tvTitle2.setText(artigo.getTitle());
        tvStatus2.setText(artigo.getStatus());
        tvContent2.setText(artigo.getContent());
        tvVerified.setText(artigo.getVerifiedContent());

        Picasso.get().load(artigo.getImg()).into(ivImg2);

        if(!artigo.getStatus().equals("Comprovado")) {
            ivBgImg2.setImageResource(R.drawable.ic_red_bg);
        }else if (artigo.getStatus().equals("Sátira")) {
            ivBgImg2.setImageAlpha(0);
        }
    }


}