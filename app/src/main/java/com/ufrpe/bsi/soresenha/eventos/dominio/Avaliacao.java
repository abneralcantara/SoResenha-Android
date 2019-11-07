package com.ufrpe.bsi.soresenha.eventos.dominio;

public class Avaliacao {
    public String idUser;
    public String idEvento;
    public TipoAvaliacao tipoAvaliacao;

    public Avaliacao(){}

    public Avaliacao(String idUser, String idEvento, TipoAvaliacao tipoAvaliacao){
        this.idUser = idUser;
        this.idEvento = idEvento;
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public TipoAvaliacao getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public void setTipoAvaliacao(TipoAvaliacao tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }


}
