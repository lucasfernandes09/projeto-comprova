package com.app.projetocomprova.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.projetocomprova.activities.SplashScreenActivity;
import com.app.projetocomprova.model.Artigos;
import com.app.projetocomprova.R;
import com.app.projetocomprova.RecuperarArtigos;
import com.app.projetocomprova.adapters.ArtigosAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private RecyclerView rvArtigos;
    private Button btnMais;
    private ArtigosAdapter adapter;
    private String URL = "https://projetocomprova.com.br/page/";
    private List<Artigos> listaDeArtigos = new ArrayList<>();
    private ProgressBar pbLoadBtnMais;
    private TextView tvAbraji, tvFirstDraft;
    final private String ABRAJI_LINK = "https://abraji.org.br/";
    final private String FD_LINK = "https://firstdraftnews.org/";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //referenciação
        rvArtigos = view.findViewById(R.id.rvArtigos);
        btnMais = view.findViewById(R.id.btnMais);
        pbLoadBtnMais = view.findViewById(R.id.pbLoadBtnMais);
        tvAbraji = view.findViewById(R.id.tvAbraji); tvFirstDraft = view.findViewById(R.id.tvFirstDraft);

        //receber lista de artigos de SplashScreen
        listaDeArtigos = getActivity().getIntent().getParcelableArrayListExtra("artigos");

        setClickFooter();

        exibirArtigos(listaDeArtigos);

        return view;
    }


    public void exibirArtigos(List<Artigos> listaDeArtigos) {
        adapter = new ArtigosAdapter(listaDeArtigos, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvArtigos.setLayoutManager(layoutManager);
        rvArtigos.setHasFixedSize(true);
        rvArtigos.setAdapter(adapter);

        setClickBtnMais();
        pbLoadBtnMais.setVisibility(View.GONE);
    }

    private void setClickBtnMais() {
        btnMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //n° de artigos atuais
                int pag = adapter.getItemCount()/10 + 1;
                //busca quando > 10
                if(pag > 1) {
                    pbLoadBtnMais.setVisibility(View.VISIBLE);
                    recuperarArtigos(URL + pag);
                }
            }
        });
    }

    private void recuperarArtigos(String url) {
        RecuperarArtigos recuperarArtigos = new RecuperarArtigos(this, url, listaDeArtigos);
        recuperarArtigos.execute();
    }

    private void setClickFooter() {
        tvAbraji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ABRAJI_LINK)));
            }
        });

        tvFirstDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(FD_LINK)));
            }
        });
    }

}
