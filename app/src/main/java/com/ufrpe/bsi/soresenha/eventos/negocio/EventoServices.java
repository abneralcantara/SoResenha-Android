package com.ufrpe.bsi.soresenha.eventos.negocio;

import android.content.Context;

import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.persistencia.EventoDAO;

import java.util.List;

public class EventoServices {
    private EventoDAO eventoDAO;

    public EventoServices(Context context) {
        this.eventoDAO = new EventoDAO(context);
    }

    public Evento getEvento(long eventId) {
        return eventoDAO.getEvento(eventId);
    }

    public void save(Evento evento) {
        eventoDAO.save(evento);
    }

    public long criarEvento(Evento evento) {
        return eventoDAO.cadastrarEvento(evento);
    }

    public void deletarEvento(Evento evento) { eventoDAO.deletar(evento);}

    public List<Evento> list() {
        return eventoDAO.list();
    }

}
