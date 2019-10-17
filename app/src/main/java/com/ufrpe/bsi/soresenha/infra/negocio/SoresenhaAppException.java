package com.ufrpe.bsi.soresenha.infra.negocio;

public class SoresenhaAppException extends Exception {
    public SoresenhaAppException(String mensagem) {super(mensagem);}
    public SoresenhaAppException(String mensagem, Throwable cause) {super(mensagem, cause);}
}
