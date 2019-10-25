package com.ufrpe.bsi.soresenha.eventos.gui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.negocio.EventoServices;

import java.util.List;

public class RecyclingAdapterFesta extends RecyclerView.Adapter<RecyclingAdapterFesta.RecyViewHolder> {

    private List<Evento> opcoesEventos;
    private Context context;
    private EventoServices eventoServices;

    public RecyclingAdapterFesta(List<Evento> opcoesEventos, Context context) {
        this.opcoesEventos = opcoesEventos;
        this.context = context;
        this.eventoServices = new EventoServices(context);
    }


    @NonNull
    @Override
    public RecyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycling_festa_card, viewGroup, false);
        return new RecyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyViewHolder holder, final int position) {
        holder.titleFesta.setText(opcoesEventos.get(position).getNome());
        holder.descFesta.setText("R$ " + opcoesEventos.get(position).getPreco());
        setOptionButtonListeners(holder, position);
    }

    private void setOptionButtonListeners(@NonNull RecyViewHolder holder, final int position) {
        holder.editFesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(context, EditarEventoActivity.class);
                Evento evento = opcoesEventos.get(position);
                editIntent.putExtra("EventId", evento.getId());
                context.startActivity(editIntent);
            }
        });
        holder.deleteFesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventoServices.deletarEvento(opcoesEventos.get(position));
                opcoesEventos.remove(position);
                Intent backMenu = new Intent(context, ListaEventoActivity.class);
                context.startActivity(backMenu);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent conIntent = new Intent(context, ConsultarEventoActivity.class);
                conIntent.putExtra("EventId", opcoesEventos.get(position).getId());
                context.startActivity(conIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (opcoesEventos != null && (!opcoesEventos.isEmpty()) ? opcoesEventos.size() : 0) ;
    }

    static class RecyViewHolder extends RecyclerView.ViewHolder {
        private TextView titleFesta;
        private TextView descFesta;
        private ImageButton editFesta;
        private ImageButton deleteFesta;

        public RecyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleFesta = itemView.findViewById(R.id.eventTitle);
            descFesta = itemView.findViewById(R.id.eventDescription);
            editFesta = itemView.findViewById(R.id.editEventList);
            deleteFesta = itemView.findViewById(R.id.deleteEventList);
        }
    }
}
