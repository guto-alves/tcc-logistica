package com.gutotech.tcclogistica.model;

public class Coleta {
    private String dataColetarAte;
    private Hora horaColetarAte;
    private String dataEmissao;
    private Remetente remetente;
    private Produto produto;
    private Destinatario destinatario;

    private Motorista motorista;

    private String observacoes;
    private String intrucoes;

    private Data dataColetada;
    private Hora horaColetada;

    public Coleta() {
    }

    public String getDataColetarAte() {
        return dataColetarAte;
    }

    public void setDataColetarAte(String dataColetarAte) {
        this.dataColetarAte = dataColetarAte;
    }

    public Hora getHoraColetarAte() {
        return horaColetarAte;
    }

    public void setHoraColetarAte(Hora horaColetarAte) {
        this.horaColetarAte = horaColetarAte;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Remetente getRemetente() {
        return remetente;
    }

    public void setRemetente(Remetente remetente) {
        this.remetente = remetente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Destinatario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Destinatario destinatario) {
        this.destinatario = destinatario;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getIntrucoes() {
        return intrucoes;
    }

    public void setIntrucoes(String intrucoes) {
        this.intrucoes = intrucoes;
    }

    public Data getDataColetada() {
        return dataColetada;
    }

    public void setDataColetada(Data dataColetada) {
        this.dataColetada = dataColetada;
    }

    public Hora getHoraColetada() {
        return horaColetada;
    }

    public void setHoraColetada(Hora horaColetada) {
        this.horaColetada = horaColetada;
    }
}
