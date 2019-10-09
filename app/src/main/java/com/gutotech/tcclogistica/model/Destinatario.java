package com.gutotech.tcclogistica.model;

public class Destinatario {
    private String nome;
    private Endereco endereco;
    private String cnpj;
    private int inscricaoEstadual;
    private Data dataEmissao;
    private Data dataEntrega;
    private String placa;

    public Destinatario() {
    }

    public Destinatario(String nome, Endereco endereco, String cnpj, int inscricaoEstadual, Data dataEmissao, Data dataEntrega, String placa) {
        this.nome = nome;
        this.endereco = endereco;
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.dataEmissao = dataEmissao;
        this.dataEntrega = dataEntrega;
        this.placa = placa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public int getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(int inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public Data getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Data dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Data getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Data dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
