package com.ufrpe.bsi.soresenha.eventos.dominio;

import java.math.BigDecimal;
import java.util.Date;

public class Evento {

    private long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int idCriador;
    private Date date;

    public Evento(){}

    public Evento(String nome, String descricao, int idCriador){
        this.nome = nome;
        this.descricao = descricao;
        this.idCriador = idCriador;
    }

    public Evento(String nome, String descricao, BigDecimal preco){
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public Evento(String nome, String descricao, BigDecimal preco, Date date){
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.date = date;
    }

    public Evento(long id, String nome, String descricao, BigDecimal preco, Date date){
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.date = date;
        this.id = id;
    }

    public Evento(long id, String nome, String descricao, BigDecimal preco){
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdCriador() {
        return idCriador;
    }

    public void setIdCriador(int idCriador) {
        this.idCriador = idCriador;
    }
}
