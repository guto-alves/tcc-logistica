package com.gutotech.tcclogistica.model;

public class CalculoImposto {
    private String valorTotalProdutos;
    private String valorFrete;
    private String valorTotalNota;

    public CalculoImposto() {
    }

    public CalculoImposto(String valorTotalProdutos, String valorFrete, String valorTotalNota) {
        this.valorTotalProdutos = valorTotalProdutos;
        this.valorFrete = valorFrete;
        this.valorTotalNota = valorTotalNota;
    }

    public String getValorTotalProdutos() {
        return valorTotalProdutos;
    }

    public void setValorTotalProdutos(String valorTotalProdutos) {
        this.valorTotalProdutos = valorTotalProdutos;
    }

    public String getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(String valorFrete) {
        this.valorFrete = valorFrete;
    }

    public String getValorTotalNota() {
        return valorTotalNota;
    }

    public void setValorTotalNota(String valorTotalNota) {
        this.valorTotalNota = valorTotalNota;
    }
}
