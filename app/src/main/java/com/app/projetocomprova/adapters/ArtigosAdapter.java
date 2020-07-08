package com.app.projetocomprova.adapters;

import android.content.Context;
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
import com.app.projetocomprova.CarregarImg;
import com.app.projetocomprova.R;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
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
        holder.tvTitle.setText(artigo.getTitle());
        holder.tvStatus.setText(artigo.getStatus());
        holder.tvContent.setText(artigo.getContent());

        //CarregarImgs carregarImgs = new CarregarImgs(holder, artigo.getImg());
        //carregarImgs.execute();
        String imgUrl = artigo.getImg();
        Picasso.get().load(imgUrl).into(holder.ivImg);
        Log.i("infoRecuperar", imgUrl);
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
        ImageView ivImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTerm = itemView.findViewById(R.id.tvTerm);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvContent = itemView.findViewById(R.id.tvContent);
            ivImg = itemView.findViewById(R.id.ivImg);
        }
    }

    public static class CarregarImgs extends AsyncTask<Void, Void, Void> {

        MyViewHolder holder;
        String imgUrl;

        public CarregarImgs(MyViewHolder holder, String imgUrl) {
            this.holder = holder;
            this.imgUrl = imgUrl;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

    }
}


