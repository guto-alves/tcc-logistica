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

    private Data dataEfetuada;
    private Hora horaEfetuada;

    public Coleta() {
    }

}
