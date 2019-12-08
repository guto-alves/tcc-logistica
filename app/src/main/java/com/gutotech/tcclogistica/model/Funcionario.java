package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Funcionario {
    public static final String ADM = "ADM";
    public static final String ROTEIRISTA = "Roteirista";
    public static final String MOTORISTA = "Motorista";

    private String image;
    private String nome;
    private String rg;
    private String cpf;
    private String dataNascimento;
    private String endereco;
    private String celular;
    private String telefone;
    private String email;
    private String cargo;

    private CNH cnh;
    private Veiculo veiculo;

    private Login login;
    private boolean online;

    public Funcionario() {
    }

    public Funcionario(String image, String nome, String rg, String cpf, String dataNascimento, String endereco, String celular, String telefone, String email, String cargo, CNH cnh, Veiculo veiculo, Login login, boolean online) {
        this.image = image;
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.celular = celular;
        this.telefone = telefone;
        this.email = email;
        this.cargo = cargo;
        this.cnh = cnh;
        this.veiculo = veiculo;
        this.login = login;
        this.online = online;
    }

    public void salvar() {
        DatabaseReference funcionarioReference = ConfigFirebase.getDatabase()
                .child("funcionario")
                .child(getLogin().getUser());

        funcionarioReference.setValue(this);
    }

    public void excluir() {
        DatabaseReference funcionarioReference = ConfigFirebase.getDatabase()
                .child("funcionario")
                .child(getLogin().getUser());

        funcionarioReference.removeValue();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public CNH getCnh() {
        return cnh;
    }

    public void setCnh(CNH cnh) {
        this.cnh = cnh;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
