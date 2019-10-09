package com.gutotech.tcclogistica.model;

public class Remetente {
    private String remetente;
    private Endereco endereco;
    private String contato;
    private String telefone;
    private String numeroPedido;
    private String veiculo;

    public Remetente() {
    }

    public Remetente(String remetente, Endereco endereco, String contato, String telefone, String numeroPedido, String veiculo) {
        this.remetente = remetente;
        this.endereco = endereco;
        this.contato = contato;
        this.telefone = telefone;
        this.numeroPedido = numeroPedido;
        this.veiculo = veiculo;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }
}
