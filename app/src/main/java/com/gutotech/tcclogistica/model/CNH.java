package com.gutotech.tcclogistica.model;

public class CNH {
    private String categoria;
    private String numeroRegistro;
    private String validade;

    public CNH() {
        this("", "", "");
    }

    public CNH(String categoria, String numeroRegistro, String validade) {
        this.categoria = categoria;
        this.numeroRegistro = numeroRegistro;
        this.validade = validade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }
}
