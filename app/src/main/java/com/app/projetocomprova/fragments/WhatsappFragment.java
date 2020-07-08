package com.app.projetocomprova.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.projetocomprova.R;
import com.squareup.picasso.Picasso;


public class WhatsappFragment extends Fragment {
    final private String NUMERO_COMRPOVA = "5511977950022";
    private Button btnEnviarWpp;

    public WhatsappFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_whatsapp, container, false);

        btnEnviarWpp = view.findViewById(R.id.btnEnviarWpp);

        ImageView iv = view.findViewById(R.id.ivTeste);
        Picasso.get().load("https://i0.wp.com/projetocomprova.com.br/wp-content/uploads/2020/07/null-17.png?fit=640%2C240&ssl=1")
                .into(iv);

        btnEnviarWpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarWpp();
            }
        });


        return view;
    }

    private void enviarWpp() {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Quero enviar um conteúdo");
            sendIntent.putExtra("jid", NUMERO_COMRPOVA + "@s.whatsapp.net");
            sendIntent.setType("text/plain");
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        }catch (Exception e) {
            Toast.makeText(getContext(), "WhatsApp não instalado", Toast.LENGTH_SHORT).show();
        }
    }
}