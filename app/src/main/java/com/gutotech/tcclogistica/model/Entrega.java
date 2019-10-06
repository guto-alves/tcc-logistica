package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Entrega {
    private String id;
    private Cliente cliente;
    private Nota produto;
    private Motorista motorista;
    private Endereco endereco;

    private enum Status {ENTREGUE, DEVOLUCAO, PEDENTE}

    private Status status;

    public Entrega() {
    }

    public Entrega(Cliente cliente, Nota produto, Motorista motorista, Endereco endereco) {
        this.cliente = cliente;
        this.produto = produto;
        this.motorista = motorista;
        this.endereco = endereco;
    }

    public void salvar() {
        DatabaseReference entregaReference = ConfigFirebase.getDatabase()
                .child("entrega")
                .child(id);
        entregaReference.setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
