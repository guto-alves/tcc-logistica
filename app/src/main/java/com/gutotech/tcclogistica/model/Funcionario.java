package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Funcionario {
    public static final String ADM = "ADM";
    public static final String ROTEIRISTA = "Roteirista";
    public static final String MOTORISTA = "Motorista";

    private int id;
    private String nome;
    private String rg;
    private String cpf;
    private String endereco;
    private String celular;
    private String email;
    private String cargo;
    private String dataNascimento;

    private Login login;
    private String ultimoLogin;
    private boolean online;

    private String cnh;
    private Veiculo veiculo;

    public Funcionario() {
    }

    public Funcionario(int id, String nome, String rg, String cpf, String endereco, String celular, String email, String cargo, String dataNascimento, Login login, String ultimoLogin, boolean online, String cnh, Veiculo veiculo) {
        this.id = id;
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.endereco = endereco;
        this.celular = celular;
        this.email = email;
        this.cargo = cargo;
        this.dataNascimento = dataNascimento;
        this.login = login;
        this.ultimoLogin = ultimoLogin;
        this.online = online;
        this.cnh = cnh;
        this.veiculo = veiculo;
    }

    public void salvar() {
        DatabaseReference funcionarioReference = ConfigFirebase.getDatabase()
                .child("funcionario")
                .child(login.getUser());

        funcionarioReference.setValue(this);
    }

    public void excluir() {
        DatabaseReference funcionarioReference = ConfigFirebase.getDatabase()
                .child("funcionario")
                .child(getLogin().getUser());

        funcionarioReference.removeValue();
    }

    public void deslogar() {
        online = false;
        salvar();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(String ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
}
