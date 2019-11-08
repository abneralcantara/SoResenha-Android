package com.ufrpe.bsi.soresenha.parceiro.negocio;

import android.content.Context;

import com.ufrpe.bsi.soresenha.parceiro.persistencia.ParceiroDAO;

public class ParceiroServices {
    private ParceiroDAO parceiroDAO;

    public ParceiroServices(Context context) {this.parceiroDAO = new ParceiroDAO();}

   //public Parceiro getParceiro(String empresa){return parceiroDAO.get(empresa);}
    //
    // public boolean CheckIfisParceiro(Usuario usuario){return parceiroDAO.check(usuario);}

}
