package com.gutotech.tcclogistica.model;

public class Endereco {
    private String endereco;
    private int numero;
    private String complemento;
    private String cep;
    private String bairro;
    private String cidade;
    private String municipio;
    private String uf;

    private String estado;

    public Endereco() {
    }

    public Endereco(String endereco, String municipio, String uf) {
        this.endereco = endereco;
        this.municipio = municipio;
        this.uf = uf;
    }

    public Endereco(String endereco, String cep, String bairro, String municipio, String uf) {
        this.endereco = endereco;
        this.cep = cep;
        this.bairro = bairro;
        this.municipio = municipio;
        this.uf = uf;
    }

    public Endereco(String endereco, int numero, String complemento, String cep, String bairro, String cidade, String municipio, String uf, String estado) {
        this.endereco = endereco;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
        this.municipio = municipio;
        this.uf = uf;
        this.estado = estado;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
