package com.gutotech.tcclogistica.model;

public class CalculoImposto {
    private double baseICMS;
    private double valorICMS;
    private double baseICMSSubstituicao;
    private double valorTotalProdutos;
    private double valorTotalNota;
    private double valorSeguro;
    private double valorFrete;
    private double desconto;
    private double outrasDespesasAcessorias;
    private double valorIPI;

    public CalculoImposto() {
    }

    public CalculoImposto(double valorTotalProdutos, double valorTotalNota, double valorFrete) {
        this.valorTotalProdutos = valorTotalProdutos;
        this.valorTotalNota = valorTotalNota;
        this.valorFrete = valorFrete;
    }

    public CalculoImposto(double baseICMS, double valorICMS, double baseICMSSubstituicao, double valorTotalProdutos, double valorTotalNota, double valorSeguro, double valorFrete, double desconto, double outrasDespesasAcessorias, double valorIPI) {
        this.baseICMS = baseICMS;
        this.valorICMS = valorICMS;
        this.baseICMSSubstituicao = baseICMSSubstituicao;
        this.valorTotalProdutos = valorTotalProdutos;
        this.valorTotalNota = valorTotalNota;
        this.valorSeguro = valorSeguro;
        this.valorFrete = valorFrete;
        this.desconto = desconto;
        this.outrasDespesasAcessorias = outrasDespesasAcessorias;
        this.valorIPI = valorIPI;
    }

    public double getBaseICMS() {
        return baseICMS;
    }

    public void setBaseICMS(double baseICMS) {
        this.baseICMS = baseICMS;
    }

    public double getValorICMS() {
        return valorICMS;
    }

    public void setValorICMS(double valorICMS) {
        this.valorICMS = valorICMS;
    }

    public double getBaseICMSSubstituicao() {
        return baseICMSSubstituicao;
    }

    public void setBaseICMSSubstituicao(double baseICMSSubstituicao) {
        this.baseICMSSubstituicao = baseICMSSubstituicao;
    }

    public double getValorTotalProdutos() {
        return valorTotalProdutos;
    }

    public void setValorTotalProdutos(double valorTotalProdutos) {
        this.valorTotalProdutos = valorTotalProdutos;
    }

    public double getValorTotalNota() {
        return valorTotalNota;
    }

    public void setValorTotalNota(double valorTotalNota) {
        this.valorTotalNota = valorTotalNota;
    }

    public double getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(double valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    public double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double getOutrasDespesasAcessorias() {
        return outrasDespesasAcessorias;
    }

    public void setOutrasDespesasAcessorias(double outrasDespesasAcessorias) {
        this.outrasDespesasAcessorias = outrasDespesasAcessorias;
    }

    public double getValorIPI() {
        return valorIPI;
    }

    public void setValorIPI(double valorIPI) {
        this.valorIPI = valorIPI;
    }
}
