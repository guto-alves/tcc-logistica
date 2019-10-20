package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Nota {
    private int id;
    private String nome;
    private String descricao;
    private String naturezaDaOperacao;
    private Endereco enderecoEntrega;
    private double valorTotalNotas;
    private double valorTotalProdutos;
    private int quantidade;
    private Transportador transportador;
    private String dadosAdicionais;

    private boolean estoque;

    public Nota() {
    }

    public Nota(int id, String nome, String descricao, String naturezaDaOperacao, Endereco enderecoEntrega, double valorTotalNotas, double valorTotalProdutos, int quantidade, Transportador transportador, String dadosAdicionais, boolean estoque) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.naturezaDaOperacao = naturezaDaOperacao;
        this.enderecoEntrega = enderecoEntrega;
        this.valorTotalNotas = valorTotalNotas;
        this.valorTotalProdutos = valorTotalProdutos;
        this.quantidade = quantidade;
        this.transportador = transportador;
        this.dadosAdicionais = dadosAdicionais;
        this.estoque = estoque;
    }

    public void salvar() {
        DatabaseReference clienteReference = ConfigFirebase.getDatabase()
                .child("produto")
                .child(nome);

        clienteReference.setValue(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNaturezaDaOperacao() {
        return naturezaDaOperacao;
    }

    public void setNaturezaDaOperacao(String naturezaDaOperacao) {
        this.naturezaDaOperacao = naturezaDaOperacao;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public double getValorTotalNotas() {
        return valorTotalNotas;
    }

    public void setValorTotalNotas(double valorTotalNotas) {
        this.valorTotalNotas = valorTotalNotas;
    }

    public double getValorTotalProdutos() {
        return valorTotalProdutos;
    }

    public void setValorTotalProdutos(double valorTotalProdutos) {
        this.valorTotalProdutos = valorTotalProdutos;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Transportador getTransportador() {
        return transportador;
    }

    public void setTransportador(Transportador transportador) {
        this.transportador = transportador;
    }

    public String getDadosAdicionais() {
        return dadosAdicionais;
    }

    public void setDadosAdicionais(String dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }

    public boolean isEstoque() {
        return estoque;
    }

    public void setEstoque(boolean estoque) {
        this.estoque = estoque;
    }
}
