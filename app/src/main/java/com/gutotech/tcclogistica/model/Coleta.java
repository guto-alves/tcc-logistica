package com.gutotech.tcclogistica.model;

public class Coleta {
    private Motorista motorista;
    private Nota produto;
    private Endereco endereco;

    public Coleta() {
    }

    public Coleta(Motorista motorista, Nota produto, Endereco endereco) {
        this.motorista = motorista;
        this.produto = produto;
        this.endereco = endereco;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Nota getProduto() {
        return produto;
    }

    public void setProduto(Nota produto) {
        this.produto = produto;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
