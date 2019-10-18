package com.gutotech.tcclogistica.model;

public class Coleta {
    private Data dataEmissao;
    private Data dataColetarAte;
    private Hora horaColetarAte;
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

}
