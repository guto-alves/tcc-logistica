package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Motorista extends Funcionario {
    private int cnh;

    private Veiculo veiculo;

    public Motorista() {
    }

    public Motorista(String id, String nome, int celular, int email, int rg, int cpf, int dataNascimento, String cargo, int cnh, Veiculo veiculo) {
        super(id, nome, celular, email, rg, cpf, dataNascimento, cargo);
        this.cnh = cnh;
        this.veiculo = veiculo;
    }

    public void salvar() {
        DatabaseReference clienteReference = ConfigFirebase.getDatabase()
                .child("funcionario")
                .child(getId());

        clienteReference.setValue(this);
    }

    public int getCnh() {
        return cnh;
    }

    public void setCnh(int cnh) {
        this.cnh = cnh;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
}
