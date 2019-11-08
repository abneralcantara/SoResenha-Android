package com.ufrpe.bsi.soresenha.eventos.gui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.dominio.Avaliacao;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;
import com.ufrpe.bsi.soresenha.usuario.negocio.UsuarioServices;

import java.util.List;

public class RecyclingAdapterParticipante extends RecyclerView.Adapter<RecyclingAdapterParticipante.RecyViewHolder> {
    private List<Avaliacao> opcoesUsuarios;
    private UsuarioServices usuarioServices;


    public RecyclingAdapterParticipante(List<Avaliacao> opcoesUsuarios) {
        this.opcoesUsuarios = opcoesUsuarios;

    }

    @NonNull
    @Override
    public RecyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycling_participantes_card, viewGroup, false);
        this.usuarioServices = new UsuarioServices(view.getContext());
        return new RecyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyViewHolder holder, int position) {
        Usuario usuario = usuarioServices.getByID(opcoesUsuarios.get(position).getIdUser());
        holder.titlePartipante.setText(usuario.getNome());
    }

    @Override
    public int getItemCount() {
        return (opcoesUsuarios != null && (!opcoesUsuarios.isEmpty()) ? opcoesUsuarios.size() : 0) ;
    }

    static class RecyViewHolder extends RecyclerView.ViewHolder {
        private TextView titlePartipante;

        public RecyViewHolder(@NonNull View itemView) {
            super(itemView);
            titlePartipante = itemView.findViewById(R.id.nomeParticipante);
        }
    }
}
