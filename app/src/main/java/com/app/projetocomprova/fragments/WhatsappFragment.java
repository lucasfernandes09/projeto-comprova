package com.app.projetocomprova.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
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

import java.util.List;


public class WhatsappFragment extends Fragment {
    final private String NUMERO_COMRPOVA = "5511977950022";
    private Button btnEnviarWpp;

    public WhatsappFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_whatsapp, container, false);

        //ref
        btnEnviarWpp = view.findViewById(R.id.btnEnviarWpp);

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
            Intent wppIntent = new Intent();
            wppIntent.setAction(Intent.ACTION_SEND);
            wppIntent.putExtra(Intent.EXTRA_TEXT, "Quero enviar um conteúdo");
            wppIntent.putExtra("jid", NUMERO_COMRPOVA + "@s.whatsapp.net");
            wppIntent.setType("text/plain");
            wppIntent.setPackage("com.whatsapp");
            startActivity(wppIntent);
        }catch (Exception e) {
            Toast.makeText(getContext(), "WhatsApp não instalado", Toast.LENGTH_SHORT).show();
        }
    }
}