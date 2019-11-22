package com.ufrpe.bsi.soresenha.eventos.gui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.avaliacao.dominio.Avaliacao;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

import java.util.List;

public class RecyclingAdapterParticipanteFotos extends RecyclerView.Adapter<RecyclingAdapterParticipanteFotos.RecyViewHolderFotos> {
    private List<Avaliacao> opcoesUsuarios;

    public RecyclingAdapterParticipanteFotos(List<Avaliacao> opcoesUsuarios) {
        this.opcoesUsuarios = opcoesUsuarios;

    }

    @NonNull
    @Override
    public RecyViewHolderFotos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycling_participantes_card, viewGroup, false);
        return new RecyViewHolderFotos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyViewHolderFotos holder, int position) {
        Usuario usuario = opcoesUsuarios.get(position).getUsuario();
        holder.titlePartipante.setText(usuario.getNome());
    }

    @Override
    public int getItemCount() {
        return (opcoesUsuarios != null && (!opcoesUsuarios.isEmpty()) ? opcoesUsuarios.size() : 0) ;
    }

    public List<Avaliacao> getItems() {
        return opcoesUsuarios;
    }

    static class RecyViewHolderFotos extends RecyclerView.ViewHolder {
        private TextView titlePartipante;

        public RecyViewHolderFotos(@NonNull View itemView) {
            super(itemView);
            titlePartipante = itemView.findViewById(R.id.nomeParticipante);
        }
    }
}
