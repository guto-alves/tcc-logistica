package com.gutotech.tcclogistica.model;

public class CNH {
    private String cnh;
    private String numeroRegistro;
    private String categoria;
    private String dataEmissao;
    private String dataValidade;

    public CNH() {
    }

    public CNH(String cnh, String numeroRegistro, String categoria, String dataEmissao, String dataValidade) {
        this.cnh = cnh;
        this.numeroRegistro = numeroRegistro;
        this.categoria = categoria;
        this.dataEmissao = dataEmissao;
        this.dataValidade = dataValidade;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }
}
