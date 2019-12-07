package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Nota {
    private String id;
    private String numero;
    private String dataEmissao;
    private String dataRecebimento;
    private String naturezaDaOperacao;
    private String inscricaoEstadual;
    private String cnpj;

    private Destinatario destinatario;

    private CalculoImposto calculoImposto;

    private Transportador transportador;

    private boolean estoque;

    public Nota() {
    }

    public Nota(String id, String numero, String dataEmissao, String dataRecebimento, String naturezaDaOperacao, String inscricaoEstadual, String cnpj, Destinatario destinatario, CalculoImposto calculoImposto, Transportador transportador, boolean estoque) {
        this.id = id;
        this.numero = numero;
        this.dataEmissao = dataEmissao;
        this.dataRecebimento = dataRecebimento;
        this.naturezaDaOperacao = naturezaDaOperacao;
        this.inscricaoEstadual = inscricaoEstadual;
        this.cnpj = cnpj;
        this.destinatario = destinatario;
        this.calculoImposto = calculoImposto;
        this.transportador = transportador;
        this.estoque = estoque;
    }

    public void salvar() {
        DatabaseReference notaReference = ConfigFirebase.getDatabase()
                .child("nota")
                .child(id);

        notaReference.setValue(this);
    }

    public void excluir() {
        DatabaseReference notaReference = ConfigFirebase.getDatabase()
                .child("nota")
                .child(id);

        notaReference.removeValue();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(String dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public String getNaturezaDaOperacao() {
        return naturezaDaOperacao;
    }

    public void setNaturezaDaOperacao(String naturezaDaOperacao) {
        this.naturezaDaOperacao = naturezaDaOperacao;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Destinatario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Destinatario destinatario) {
        this.destinatario = destinatario;
    }

    public CalculoImposto getCalculoImposto() {
        return calculoImposto;
    }

    public void setCalculoImposto(CalculoImposto calculoImposto) {
        this.calculoImposto = calculoImposto;
    }

    public Transportador getTransportador() {
        return transportador;
    }

    public void setTransportador(Transportador transportador) {
        this.transportador = transportador;
    }

    public boolean isEstoque() {
        return estoque;
    }

    public void setEstoque(boolean estoque) {
        this.estoque = estoque;
    }
}
