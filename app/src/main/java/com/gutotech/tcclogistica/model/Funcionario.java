package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Funcionario {
    public static final String ADM = "ADM";
    public static final String ROTEIRISTA = "Roteirista";
    public static final String MOTORISTA = "Motorista";

    private String nome;
    private String rg;
    private String cpf;
    private String dataNascimento;
    private String endereco;
    private String celular;
    private String telefone;
    private String email;
    private boolean profileImage;
    private String cargo;

    private String cnh;
    private Veiculo veiculo;

    private Login login;
    private boolean online;

    public Funcionario() {
    }

    public Funcionario(String nome, String rg, String cpf, String dataNascimento, String endereco, String celular, String telefone, String email, boolean profileImage, String cargo, Login login, boolean online, String cnh, Veiculo veiculo) {
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.celular = celular;
        this.telefone = telefone;
        this.email = email;
        this.profileImage = profileImage;
        this.cargo = cargo;
        this.login = login;
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

    public boolean isProfileImage() {
        return profileImage;
    }

    public void setProfileImage(boolean profileImage) {
        this.profileImage = profileImage;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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
