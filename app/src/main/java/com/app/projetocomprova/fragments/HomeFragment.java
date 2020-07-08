package com.app.projetocomprova.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.projetocomprova.Artigos;
import com.app.projetocomprova.R;
import com.app.projetocomprova.RecuperarArtigos;
import com.app.projetocomprova.adapters.ArtigosAdapter;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class HomeFragment extends Fragment {
    private List<Artigos> listaDeArtigos;
    private RecyclerView rvArtigos;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //referenciação
        rvArtigos = view.findViewById(R.id.rvArtigos);

        RecuperarArtigos recuperarArtigos = new RecuperarArtigos();
        recuperarArtigos.execute();
        try {
            listaDeArtigos = recuperarArtigos.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        exibirArtigos();

        return view;
    }

    public void exibirArtigos() {
        ArtigosAdapter adapter = new ArtigosAdapter(listaDeArtigos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvArtigos.setLayoutManager(layoutManager);
        rvArtigos.setHasFixedSize(true);
        rvArtigos.setAdapter(adapter);


    }
}
