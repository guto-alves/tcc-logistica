package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Nota {
    private int cod;
    private String nome;
    private String descricao;
    private double valor;
    private int quantidade;
    private Data dataChegada;
    private Data dataEntrega;
    private double pesoBruto;
    private double pesoLiquido;

    public Nota() {
    }

    public Nota(int cod, String nome, String descricao, double valor, Data dataChegada, Data dataEntrega) {
        this.cod = cod;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.dataChegada = dataChegada;
        this.dataEntrega = dataEntrega;
    }

    private void salvar() {
        DatabaseReference clienteReference = ConfigFirebase.getDatabase()
                .child("produto")
                .child(nome);

        clienteReference.setValue(this);
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Data getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(Data dataChegada) {
        this.dataChegada = dataChegada;
    }

    public Data getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Data dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
}
