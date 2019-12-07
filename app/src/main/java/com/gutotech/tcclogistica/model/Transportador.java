package com.gutotech.tcclogistica.model;

public class Transportador {
    private String nome;
    private String fretePorConta;
    private String codANTT;
    private String placaDoVeiculo;
    private String cnpj;

    private String endereco;
    private String municipio;
    private String uf;
    private String inscricaoEstadual;

    public Transportador() {
    }

    public Transportador(String nome, String fretePorConta, String codANTT, String placaDoVeiculo, String cnpj, String endereco, String municipio, String uf, String inscricaoEstadual, String quantidade, String especie, String marca, String numero, String pesoBruto, String pesoLiquido) {
        this.nome = nome;
        this.fretePorConta = fretePorConta;
        this.codANTT = codANTT;
        this.placaDoVeiculo = placaDoVeiculo;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.municipio = municipio;
        this.uf = uf;
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFretePorConta() {
        return fretePorConta;
    }

    public void setFretePorConta(String fretePorConta) {
        this.fretePorConta = fretePorConta;
    }

    public String getCodANTT() {
        return codANTT;
    }

    public void setCodANTT(String codANTT) {
        this.codANTT = codANTT;
    }

    public String getPlacaDoVeiculo() {
        return placaDoVeiculo;
    }

    public void setPlacaDoVeiculo(String placaDoVeiculo) {
        this.placaDoVeiculo = placaDoVeiculo;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }
}
