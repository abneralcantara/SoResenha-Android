package com.ufrpe.bsi.soresenha.eventos.negocio;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;

import java.util.List;

public class RecyclingAdapterfesta extends RecyclerView.Adapter<RecyclingAdapterfesta.RecyViewHolder> {

    private List<Evento> opcoes_eventos;

    public RecyclingAdapterfesta(List<Evento> opcoes_eventos) {
        this.opcoes_eventos = opcoes_eventos;
    }


    @NonNull
    @Override
    public RecyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycling_festa_card, viewGroup, false);

        return new RecyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyViewHolder holder, int position) {
        holder.title_festa.setText(opcoes_eventos.get(position).getNome());
        holder.desc_festa.setText(opcoes_eventos.get(position).getDescricao());
    }
    //--Tamanho da Lista--//
    @Override
    public int getItemCount() {
        return (opcoes_eventos != null && (opcoes_eventos.size() > 0) ? opcoes_eventos.size() : 0) ;
    }

    static class RecyViewHolder extends RecyclerView.ViewHolder {

        private TextView title_festa;
        private TextView desc_festa;

        public RecyViewHolder(@NonNull View itemView) {
            super(itemView);

            title_festa = itemView.findViewById(R.id.title_Festacard);
            desc_festa = itemView.findViewById(R.id.desc_Festacard);
        }
    }
}
