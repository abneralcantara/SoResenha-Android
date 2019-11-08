package com.ufrpe.bsi.soresenha.eventos.dominio;

public class Avaliacao {
    public long id;
    public long idUser;
    public long idEvento;
    public TipoAvaliacao tipoAvaliacao;

    public Avaliacao(){}

    public Avaliacao(long idUser, long idEvento, TipoAvaliacao tipoAvaliacao){
        this.idUser = idUser;
        this.idEvento = idEvento;
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(long idEvento) {
        this.idEvento = idEvento;
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
