package com.ufrpe.bsi.soresenha.eventos.negocio;

import android.content.Context;

import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.persistencia.EventoDAO;
import com.ufrpe.bsi.soresenha.usuario.persistencia.UsuarioDAO;

public class EventoServices {
    private EventoDAO eventoDAO;

    public EventoServices(Context context) {
        this.eventoDAO = new EventoDAO(context);
    }

    public Evento getEvento(String nomeEvento) {
        return eventoDAO.getEvento(nomeEvento);
    }
//essa função eh para buscar o evento pelo id
  //  public Evento getEventobyId(int numberId) {
//        return eventoDAO.getEvento(numberId);
  //  }

    public boolean checarEvento(String nomeEvento) {
        return eventoDAO.checarEvento(nomeEvento);
    }

    public long criarEvento(Evento evento) {
        return eventoDAO.cadastrarEvento(evento);
    }

    public void deletarEvento(Evento evento) { eventoDAO.deletar(evento);}

}
