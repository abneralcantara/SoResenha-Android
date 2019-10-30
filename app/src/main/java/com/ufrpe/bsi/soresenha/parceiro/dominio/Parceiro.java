package com.ufrpe.bsi.soresenha.parceiro.dominio;

import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

public class Parceiro {

    private long idParceiro;
    private Usuario userparceiro;

    public long getIdParceiro() {
        return idParceiro;
    }

    public void setIdParceiro(long idParceiro) {
        this.idParceiro = idParceiro;
    }

    public Usuario getUserparceiro() {
        return userparceiro;
    }

    public void setUserparceiro(Usuario userparceiro) {
        this.userparceiro = userparceiro;
    }

}
