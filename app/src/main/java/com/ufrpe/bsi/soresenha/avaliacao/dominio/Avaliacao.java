package com.ufrpe.bsi.soresenha.avaliacao.dominio;

import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

public class Avaliacao {
    private long id;
    private Usuario usuario;
    private Evento evento;
    private TipoAvaliacao tipoAvaliacao;

    public Avaliacao(){}

    public Avaliacao(long id, Usuario usuario, Evento evento, TipoAvaliacao tipoAvaliacao) {
        this.id = id;
        this.usuario = usuario;
        this.evento = evento;
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public TipoAvaliacao getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public void setTipoAvaliacao(TipoAvaliacao tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
