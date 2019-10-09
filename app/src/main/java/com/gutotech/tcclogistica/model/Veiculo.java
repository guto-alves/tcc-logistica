package com.gutotech.tcclogistica.model;

public class Veiculo {
    private String nome;
    private String categoria;
    private String ano;
    private String placa;

    public Veiculo() {
    }

    public Veiculo(String nome, String categoria, String ano, String placa) {
        this.nome = nome;
        this.categoria = categoria;
        this.ano = ano;
        this.placa = placa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
