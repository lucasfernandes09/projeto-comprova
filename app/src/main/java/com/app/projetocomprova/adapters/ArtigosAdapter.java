package com.app.projetocomprova.adapters;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.projetocomprova.Artigos;
import com.app.projetocomprova.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtigosAdapter extends RecyclerView.Adapter<ArtigosAdapter.MyViewHolder> {
    private List<Artigos> listaDeArtigos;
    //private Context context;

    public ArtigosAdapter(List<Artigos> listaDeArtigos) {
        this.listaDeArtigos = listaDeArtigos;
        //this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artigos_layout, parent, false);
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
        TextView tvTerm, tvDate, tvTitle, tvStatus, tvContent;
        ImageView ivImg, ivBgImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTerm = itemView.findViewById(R.id.tvTerm);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvContent = itemView.findViewById(R.id.tvContent);
            ivImg = itemView.findViewById(R.id.ivImg);
            ivBgImg = itemView.findViewById(R.id.ivBgImg);
        }
    }
}


