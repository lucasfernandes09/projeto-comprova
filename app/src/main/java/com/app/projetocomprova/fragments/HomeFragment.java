package com.app.projetocomprova.fragments;

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
    private int pag = 1;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvArtigos = view.findViewById(R.id.rvArtigos);
        btnMais = view.findViewById(R.id.btnMais);

        recuperarArtigos(URL + 1);

        return view;
    }

    private void setClick() {
        btnMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //n° de artigos atuais
                int pag = adapter.getItemCount()/10 + 1;
                //busca quando > 10
                if(pag > 1) {
                    Log.i("artigo", URL + pag);
                    recuperarArtigos(URL + pag);
                }
            }
        });
    }

    private void recuperarArtigos(String url) {
        RecuperarArtigos recuperarArtigos = new RecuperarArtigos(this, url, listaDeArtigos);
        recuperarArtigos.execute();
    }

    public void exibirArtigos(List<Artigos> listaDeArtigos) {
        adapter = new ArtigosAdapter(listaDeArtigos, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvArtigos.setLayoutManager(layoutManager);
        rvArtigos.setHasFixedSize(true);
        rvArtigos.setAdapter(adapter);
        setClick();
    }

}
