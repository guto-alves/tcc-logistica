package com.gutotech.tcclogistica.model;

public class Destinatario {
    private String nome;
    private String cnpj;

    private Endereco endereco;

    private String foneFax;

    private String inscricaoEstadual;

    private String dataEmissao, dataEntradaSaida, horaEntradaSaida;

    public Destinatario() {
    }

    public Destinatario(String nome, String cnpj, Endereco endereco, String foneFax, String inscricaoEstadual, String dataEmissao, String dataEntradaSaida, String horaEntradaSaida) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.foneFax = foneFax;
        this.inscricaoEstadual = inscricaoEstadual;
        this.dataEmissao = dataEmissao;
        this.dataEntradaSaida = dataEntradaSaida;
        this.horaEntradaSaida = horaEntradaSaida;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getFoneFax() {
        return foneFax;
    }

    public void setFoneFax(String foneFax) {
        this.foneFax = foneFax;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }


    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getDataEntradaSaida() {
        return dataEntradaSaida;
    }

    public void setDataEntradaSaida(String dataEntradaSaida) {
        this.dataEntradaSaida = dataEntradaSaida;
    }

    public String getHoraEntradaSaida() {
        return horaEntradaSaida;
    }

    public void setHoraEntradaSaida(String horaEntradaSaida) {
        this.horaEntradaSaida = horaEntradaSaida;
    }
}
