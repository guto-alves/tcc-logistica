package com.gutotech.tcclogistica.model;

public class CNH {
    private String categoria;
    private String numeroRegistro;
    private String validade;
    private String primeiraHabilitacao;

    private String local;
    private String dataEmissao;

    public CNH() {
    }

    public CNH(String categoria, String numeroRegistro, String validade, String primeiraHabilitacao, String local, String dataEmissao) {
        this.categoria = categoria;
        this.numeroRegistro = numeroRegistro;
        this.validade = validade;
        this.primeiraHabilitacao = primeiraHabilitacao;
        this.local = local;
        this.dataEmissao = dataEmissao;
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

    public String getPrimeiraHabilitacao() {
        return primeiraHabilitacao;
    }

    public void setPrimeiraHabilitacao(String primeiraHabilitacao) {
        this.primeiraHabilitacao = primeiraHabilitacao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }
}
