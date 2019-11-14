package com.ufrpe.bsi.soresenha.eventos.gui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.negocio.EventoServices;
import com.ufrpe.bsi.soresenha.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.soresenha.infra.negocio.SoresenhaAppException;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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
        Evento evento = opcoesEventos.get(position);
        holder.titleFesta.setText(evento.getNome());
        NumberFormat realFormat = NumberFormat.getCurrencyInstance(new Locale( "pt", "BR" ));
        String formatted = realFormat.format(evento.getPreco());
        holder.descFesta.setText(formatted);
        if (!evento.getImagens().isEmpty()) {
            holder.mainImageEvento.setImageBitmap(evento.getImagens().get(0).getImagem());
        } else {
            holder.mainImageEvento.setImageResource(R.mipmap.imagecvjas);
        }
        setOptionButtonListeners(holder, position);
    }

    private void setOptionButtonListeners(@NonNull final RecyViewHolder holder, final int position) {
        if (SessaoUsuario.instance.isParceiro()
                && SessaoUsuario.instance.getUsuario().getId() == opcoesEventos.get(position).getCriador().getId()) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu popup = new PopupMenu(holder.itemView.getContext(), holder.itemView.findViewById(R.id.listPopupAnchor));
                    popup.inflate(R.menu.evento_options);
                    popupActions(popup, position);
                    popup.show();
                    return true;
                }
            });
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent conIntent = new Intent(context, ConsultarEventoActivity.class);
                conIntent.putExtra("EventId", opcoesEventos.get(position).getId());
                conIntent.setFlags(conIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(conIntent);
            }
        });
    }

    private void popupActions(PopupMenu popup, final int position) {
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.editEvent:
                        moveToEdit(position);
                        break;
                    case R.id.deleteEvent:
                        deleteEvent(position);
                        break;
                }
                return false;
            }
        });
    }

    private void deleteEvent(int position) {
        try {
            eventoServices.deletar(opcoesEventos.get(position));
        } catch (SoresenhaAppException e) {
            Toast.makeText(this.context, "Você não tem permissão para alterar eventos", Toast.LENGTH_LONG).show();
            this.context.startActivity(new Intent(this.context, ListaEventoActivity.class));
            e.printStackTrace();
        }
        opcoesEventos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, opcoesEventos.size());

    }

    private void moveToEdit(int position) {
        Intent editIntent = new Intent(context, SalvarEventoActivity.class);
        Evento evento = opcoesEventos.get(position);
        editIntent.putExtra("EventId", evento.getId());
        context.startActivity(editIntent);
    }

    @Override
    public int getItemCount() {
        return (opcoesEventos != null && (!opcoesEventos.isEmpty()) ? opcoesEventos.size() : 0) ;
    }

    static class RecyViewHolder extends RecyclerView.ViewHolder {
        private TextView titleFesta;
        private TextView descFesta;
        private ImageView mainImageEvento;

        public RecyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleFesta = itemView.findViewById(R.id.eventTitle);
            mainImageEvento = itemView.findViewById(R.id.mainImageEvento);
            descFesta = itemView.findViewById(R.id.eventDescription);
        }
    }
}
