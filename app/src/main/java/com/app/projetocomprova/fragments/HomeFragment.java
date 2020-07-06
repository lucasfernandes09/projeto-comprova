package com.app.projetocomprova.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.projetocomprova.Artigos;
import com.app.projetocomprova.R;
import com.app.projetocomprova.RecuperarArtigos;

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

    }
}
