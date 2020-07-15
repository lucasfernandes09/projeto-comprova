package com.app.projetocomprova.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.projetocomprova.R;


public class ContatoFragment extends Fragment {
    private LinearLayout l_wpp, l_fc, l_tt, l_email;
    final private String NUMERO_COMRPOVA = "5511977950022";
    final private String FACEBOOK_COMPROVA = "https://facebook.com/ComprovaBR";
    final private String TWITTER_COMPROVA = "https://twitter.com/comprova";
    final private String EMAIL_COMPROVA = "comprova@abraji.org.br";

    public ContatoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contato, container, false);

        //ref
        l_wpp = view.findViewById(R.id.l_wpp);l_fc = view.findViewById(R.id.l_fc);
        l_tt = view.findViewById(R.id.l_tt);l_email = view.findViewById(R.id.l_email);

        setClickWpp();
        setClickFc();
        setClickTt();
        setClickEmail();

        return view;
    }

    private void setClickWpp() {
        l_wpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }

    private void setClickFc() {
        l_fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                try{
                    facebookIntent.setData(Uri.parse("fb://facewebmodal/f?href=" + FACEBOOK_COMPROVA));
                    startActivity(facebookIntent);
                }catch (Exception e) {
                    facebookIntent.setData(Uri.parse(FACEBOOK_COMPROVA));
                    startActivity(facebookIntent);
                }
            }
        });
    }

    private void setClickTt() {
        l_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(TWITTER_COMPROVA));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void setClickEmail() {
        l_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL_COMPROVA});
                email.putExtra(Intent.EXTRA_SUBJECT, "Quero enviar um conteúdo");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Enviar email com: "));
            }
        });
    }

}