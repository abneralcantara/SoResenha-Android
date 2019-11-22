package com.ufrpe.bsi.soresenha.eventos.gui;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.dominio.ImagemEvento;

import java.util.List;

public class RecyclingAdapterFotosFesta extends RecyclerView.Adapter<RecyclingAdapterFotosFesta.RecyViewHolderFotos>{
    private List<ImagemEvento> opcoesImages;

    public RecyclingAdapterFotosFesta(List<ImagemEvento> opcoesImages) {
        this.opcoesImages = opcoesImages;
    }

    @NonNull
    @Override
    public RecyViewHolderFotos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycling_festa_fotos, viewGroup, false);
        return new RecyViewHolderFotos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyViewHolderFotos holder, int position) {
        Bitmap fotoevento = opcoesImages.get(position).getImagem();
        holder.imageEvento.setImageBitmap(fotoevento);
    }

    @Override
    public int getItemCount() {
        return (opcoesImages != null && (!opcoesImages.isEmpty()) ? opcoesImages.size() : 0) ;
    }

    public List<ImagemEvento> getItems() {
        return opcoesImages;
    }

    static class RecyViewHolderFotos extends RecyclerView.ViewHolder {
        private ImageView imageEvento;

        public RecyViewHolderFotos(@NonNull View itemView) {
            super(itemView);
            imageEvento = itemView.findViewById(R.id.image_recycler_card);
        }
    }
}
