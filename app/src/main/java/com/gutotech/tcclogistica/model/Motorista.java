package com.gutotech.tcclogistica.model;

public class Motorista extends Funcionario {
    private int cnh;
    private Veiculo veiculo;

    public Motorista() {
    }

    public Motorista(String nome, int celular, int email, int rg, int cpf, int dataNascimento, String cargo, Register register, int cnh, Veiculo veiculo) {
        super(nome, celular, email, rg, cpf, dataNascimento, cargo, register);
        this.cnh = cnh;
        this.veiculo = veiculo;
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
