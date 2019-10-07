package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Funcionario {
    private String nome;
    private int celular;
    private int email;
    private int rg;
    private int cpf;
    private int dataNascimento;
    private String cargo;

    private Register register;

    public Funcionario() {
    }

    public Funcionario(String nome, int celular, int email, int rg, int cpf, int dataNascimento, String cargo, Register register) {
        this.nome = nome;
        this.celular = celular;
        this.email = email;
        this.rg = rg;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.cargo = cargo;
        this.register = register;
    }

    public void salvar() {
        DatabaseReference clienteReference = ConfigFirebase.getDatabase()
                .child("funcionario")
                .child(register.getUser());

        clienteReference.setValue(this);
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEmail() {
        return email;
    }

    public void setEmail(int email) {
        this.email = email;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public int getRg() {
        return rg;
    }

    public void setRg(int rg) {
        this.rg = rg;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(int dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
