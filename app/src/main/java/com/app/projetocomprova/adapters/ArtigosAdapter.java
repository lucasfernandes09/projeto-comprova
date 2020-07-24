package com.app.projetocomprova.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.projetocomprova.model.Artigos;
import com.app.projetocomprova.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtigosAdapter extends RecyclerView.Adapter<ArtigosAdapter.MyViewHolder> {

    private List<Artigos> listaDeArtigos;
    private Context context;
    private ArtigoListener artigoListener;

    public ArtigosAdapter(List<Artigos> listaDeArtigos, Context context, ArtigoListener artigoListener) {
        this.listaDeArtigos = listaDeArtigos;
        this.context = context;
        this.artigoListener = artigoListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_artigo, parent, false);
        return new MyViewHolder(itemView, artigoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Artigos artigo = listaDeArtigos.get(position);

        holder.tvTerm.setText(artigo.getTerm());
        holder.tvDate.setText(artigo.getDate());
        holder.tvTitle.setText(artigo.getTitle());
        holder.tvStatus.setText(artigo.getStatus());
        holder.tvContent.setText(artigo.getContent());

        Picasso.get().load(artigo.getImg()).into(holder.ivImg);

        if(!artigo.getStatus().equals("Comprovado")) {
            holder.ivBgImg.setImageResource(R.drawable.ic_red_bg);
        }else if (artigo.getStatus().equals("Sátira")) {
            holder.ivBgImg.setImageAlpha(0);
        }

        setClickFacebook(holder, artigo.getShareFacebook());
        setClickTwitter(holder, artigo.getShareTwitter());
        setClickWpp(holder, artigo);
    }

    @Override
    public int getItemCount() {
        return listaDeArtigos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTerm, tvDate, tvTitle, tvStatus, tvContent;
        ImageView ivImg, ivBgImg, ivFacebook, ivTwitter, ivWpp;
        ArtigoListener artigoListener;

        public MyViewHolder(@NonNull View itemView, ArtigoListener artigoListener) {
            super(itemView);
            tvTerm = itemView.findViewById(R.id.tvTerm);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvContent = itemView.findViewById(R.id.tvContent);
            ivImg = itemView.findViewById(R.id.ivImg);
            ivBgImg = itemView.findViewById(R.id.ivBgImg);
            ivFacebook = itemView.findViewById(R.id.iv_Facebook);
            ivTwitter = itemView.findViewById(R.id.iv_Twitter);
            ivWpp = itemView.findViewById(R.id.iv_Wpp);

            this.artigoListener = artigoListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            artigoListener.click(getAdapterPosition());
        }
    }

    public interface ArtigoListener {
        void click(int position);
    }

    private void setClickFacebook(MyViewHolder holder, final String linkFacebook) {
        holder.ivFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                try{
                    facebookIntent.setData(Uri.parse("fb://facewebmodal/f?href=" + linkFacebook));
                    context.startActivity(facebookIntent);
                }catch (Exception e) {
                    facebookIntent.setData(Uri.parse(linkFacebook));
                    context.startActivity(facebookIntent);
                }
            }
        });
    }

    private void setClickTwitter(MyViewHolder holder, final String linkTwitter) {
        holder.ivTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkTwitter));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void setClickWpp(MyViewHolder holder, final Artigos artigo) {
        holder.ivWpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent wppIntent = new Intent(Intent.ACTION_SEND);
                    wppIntent.putExtra(Intent.EXTRA_TEXT, artigo.getLink());
                    wppIntent.setType("text/plain");
                    wppIntent.setPackage("com.whatsapp");
                    context.startActivity(wppIntent);
                }catch (Exception e) {
                    Toast.makeText(context, "WhatsApp não instalado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}


