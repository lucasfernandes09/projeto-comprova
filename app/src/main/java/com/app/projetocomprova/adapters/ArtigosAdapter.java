package com.app.projetocomprova.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.projetocomprova.model.Artigos;
import com.app.projetocomprova.R;
import com.app.projetocomprova.activities.ArtigoActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtigosAdapter extends RecyclerView.Adapter<ArtigosAdapter.MyViewHolder> {
    private List<Artigos> listaDeArtigos;
    private Context context;
    //private Artigos artigo;

    public ArtigosAdapter(List<Artigos> listaDeArtigos, Context context) {
        this.listaDeArtigos = listaDeArtigos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_artigo, parent, false);
        return new MyViewHolder(itemView);
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

        setClick(holder, artigo);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTerm, tvDate, tvTitle, tvStatus, tvContent, tvVejaMais;
        ImageView ivImg, ivBgImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTerm = itemView.findViewById(R.id.tvTerm);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvVejaMais = itemView.findViewById(R.id.tvVejaMais);
            ivImg = itemView.findViewById(R.id.ivImg);
            ivBgImg = itemView.findViewById(R.id.ivBgImg);
        }
    }

    public void setClick(MyViewHolder holder, final Artigos artigo) {
        holder.tvVejaMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArtigoActivity.class);
                intent.putExtra("artigo", artigo);
                context.startActivity(intent);
            }
        });
    }
}


