package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Entrega {
    private String id;
    private Nota produto;
    private Motorista motorista;
    private Endereco endereco;
    private Hora horarioSaida;
    private Hora horarioChegada;

    private enum Status {ENTREGUE, DEVOLUCAO, PEDENTE}

    private Status status;

    public Entrega() {
    }

    public Entrega(Nota produto, Motorista motorista, Endereco endereco) {
        this.produto = produto;
        this.motorista = motorista;
        this.endereco = endereco;
    }

    public void salvar() {
        DatabaseReference entregaReference = ConfigFirebase.getDatabase()
                .child("entrega")
                .push();
        entregaReference.setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Nota getProduto() {
        return produto;
    }

    public void setProduto(Nota produto) {
        this.produto = produto;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
