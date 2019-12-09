package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Coleta {
    private String id;
    private String numero;
    private String data;
    private String hora;

    // remetente
    private String nomeRemetente, enderecoRemetente, bairroRemetente, cidadeRemetente, cepRemetente,
            contatoRemetente, telefoneRemetente, numeroPedido;

    // destinatário
    private String nomeDestinatario, enderecoDestinatario, bairroDestinatario, cidadeDestinatario, cepDestinatario,
            contatoDestinatario, telefoneDestinatario;

    private Funcionario motorista;

    private String observacoes;

    private Status status;
    private ResultadoViagem resultadoViagem;

    public Coleta() {
    }

    public Coleta(String id, String numero, String data, String hora, String nomeRemetente, String enderecoRemetente, String bairroRemetente, String cidadeRemetente, String cepRemetente, String contatoRemetente, String telefoneRemetente, String numeroPedido, String nomeDestinatario, String enderecoDestinatario, String bairroDestinatario, String cidadeDestinatario, String cepDestinatario, String contatoDestinatario, String telefoneDestinatario, Funcionario motorista, String observacoes, Status status, ResultadoViagem resultadoViagem) {
        this.id = id;
        this.numero = numero;
        this.data = data;
        this.hora = hora;
        this.nomeRemetente = nomeRemetente;
        this.enderecoRemetente = enderecoRemetente;
        this.bairroRemetente = bairroRemetente;
        this.cidadeRemetente = cidadeRemetente;
        this.cepRemetente = cepRemetente;
        this.contatoRemetente = contatoRemetente;
        this.telefoneRemetente = telefoneRemetente;
        this.numeroPedido = numeroPedido;
        this.nomeDestinatario = nomeDestinatario;
        this.enderecoDestinatario = enderecoDestinatario;
        this.bairroDestinatario = bairroDestinatario;
        this.cidadeDestinatario = cidadeDestinatario;
        this.cepDestinatario = cepDestinatario;
        this.contatoDestinatario = contatoDestinatario;
        this.telefoneDestinatario = telefoneDestinatario;
        this.motorista = motorista;
        this.observacoes = observacoes;
        this.status = status;
        this.resultadoViagem = resultadoViagem;
    }

    public void salvar() {
        DatabaseReference coletaReference = ConfigFirebase.getDatabase()
                .child("coleta")
                .child(id);

        coletaReference.setValue(this);
    }

    public void excluir() {
        DatabaseReference coletaReference = ConfigFirebase.getDatabase()
                .child("coleta")
                .child(id);

        coletaReference.removeValue();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNomeRemetente() {
        return nomeRemetente;
    }

    public void setNomeRemetente(String nomeRemetente) {
        this.nomeRemetente = nomeRemetente;
    }

    public String getEnderecoRemetente() {
        return enderecoRemetente;
    }

    public void setEnderecoRemetente(String enderecoRemetente) {
        this.enderecoRemetente = enderecoRemetente;
    }

    public String getBairroRemetente() {
        return bairroRemetente;
    }

    public void setBairroRemetente(String bairroRemetente) {
        this.bairroRemetente = bairroRemetente;
    }

    public String getCidadeRemetente() {
        return cidadeRemetente;
    }

    public void setCidadeRemetente(String cidadeRemetente) {
        this.cidadeRemetente = cidadeRemetente;
    }

    public String getCepRemetente() {
        return cepRemetente;
    }

    public void setCepRemetente(String cepRemetente) {
        this.cepRemetente = cepRemetente;
    }

    public String getContatoRemetente() {
        return contatoRemetente;
    }

    public void setContatoRemetente(String contatoRemetente) {
        this.contatoRemetente = contatoRemetente;
    }

    public String getTelefoneRemetente() {
        return telefoneRemetente;
    }

    public void setTelefoneRemetente(String telefoneRemetente) {
        this.telefoneRemetente = telefoneRemetente;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getNomeDestinatario() {
        return nomeDestinatario;
    }

    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }

    public String getEnderecoDestinatario() {
        return enderecoDestinatario;
    }

    public void setEnderecoDestinatario(String enderecoDestinatario) {
        this.enderecoDestinatario = enderecoDestinatario;
    }

    public String getBairroDestinatario() {
        return bairroDestinatario;
    }

    public void setBairroDestinatario(String bairroDestinatario) {
        this.bairroDestinatario = bairroDestinatario;
    }

    public String getCidadeDestinatario() {
        return cidadeDestinatario;
    }

    public void setCidadeDestinatario(String cidadeDestinatario) {
        this.cidadeDestinatario = cidadeDestinatario;
    }

    public String getCepDestinatario() {
        return cepDestinatario;
    }

    public void setCepDestinatario(String cepDestinatario) {
        this.cepDestinatario = cepDestinatario;
    }

    public String getContatoDestinatario() {
        return contatoDestinatario;
    }

    public void setContatoDestinatario(String contatoDestinatario) {
        this.contatoDestinatario = contatoDestinatario;
    }

    public String getTelefoneDestinatario() {
        return telefoneDestinatario;
    }

    public void setTelefoneDestinatario(String telefoneDestinatario) {
        this.telefoneDestinatario = telefoneDestinatario;
    }

    public Funcionario getMotorista() {
        return motorista;
    }

    public void setMotorista(Funcionario motorista) {
        this.motorista = motorista;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ResultadoViagem getResultadoViagem() {
        return resultadoViagem;
    }

    public void setResultadoViagem(ResultadoViagem resultadoViagem) {
        this.resultadoViagem = resultadoViagem;
    }
}
