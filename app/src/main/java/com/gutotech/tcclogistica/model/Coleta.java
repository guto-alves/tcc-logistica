package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Coleta {
    private String id;
    private String numero;
    private String coletarEm;
    private String dataEmissao;

    // remetente
    private String nomeRemetente, enderecoRemetente, referenciaRemetente,
            bairroRemetente, cidadeRemetente, cepRemetente,
            contatoRemetente, telefoneRemetente, especieRemetente, nPedido, veiculoRemetente;

    // produto
    private String nomeProduto, peso, dimensoes, nONU;

    // destinatario
    private String nomeDestinatario, enderecoDestinatario, destinoDestinatario,
            site, placaDestinatario, semiReboqueDestinatario;

    private Funcionario motorista;

    private String observacoes, instrucoes;

    private String dataColetaEfetuada, horaColetaEfetuada;

    public enum Status {REALIZADA, PENDENTE}

    public Status status;

    public Coleta() {
    }

    public Coleta(String id, String numero, String coletarEm, String dataEmissao, String nomeRemetente, String enderecoRemetente, String referenciaRemetente, String bairroRemetente, String cidadeRemetente, String cepRemetente, String contatoRemetente, String telefoneRemetente, String especieRemetente, String nPedido, String veiculoRemetente, String nomeProduto, String peso, String dimensoes, String nONU, String nomeDestinatario, String enderecoDestinatario, String destinoDestinatario, String site, String placaDestinatario, String semiReboqueDestinatario, Funcionario motorista, String observacoes, String instrucoes, String dataColetaEfetuada, String horaColetaEfetuada, Status status) {
        this.id = id;
        this.numero = numero;
        this.coletarEm = coletarEm;
        this.dataEmissao = dataEmissao;
        this.nomeRemetente = nomeRemetente;
        this.enderecoRemetente = enderecoRemetente;
        this.referenciaRemetente = referenciaRemetente;
        this.bairroRemetente = bairroRemetente;
        this.cidadeRemetente = cidadeRemetente;
        this.cepRemetente = cepRemetente;
        this.contatoRemetente = contatoRemetente;
        this.telefoneRemetente = telefoneRemetente;
        this.especieRemetente = especieRemetente;
        this.nPedido = nPedido;
        this.veiculoRemetente = veiculoRemetente;
        this.nomeProduto = nomeProduto;
        this.peso = peso;
        this.dimensoes = dimensoes;
        this.nONU = nONU;
        this.nomeDestinatario = nomeDestinatario;
        this.enderecoDestinatario = enderecoDestinatario;
        this.destinoDestinatario = destinoDestinatario;
        this.site = site;
        this.placaDestinatario = placaDestinatario;
        this.semiReboqueDestinatario = semiReboqueDestinatario;
        this.motorista = motorista;
        this.observacoes = observacoes;
        this.instrucoes = instrucoes;
        this.dataColetaEfetuada = dataColetaEfetuada;
        this.horaColetaEfetuada = horaColetaEfetuada;
        this.status = status;
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

    public String getColetarEm() {
        return coletarEm;
    }

    public void setColetarEm(String coletarEm) {
        this.coletarEm = coletarEm;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
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

    public String getReferenciaRemetente() {
        return referenciaRemetente;
    }

    public void setReferenciaRemetente(String referenciaRemetente) {
        this.referenciaRemetente = referenciaRemetente;
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

    public String getEspecieRemetente() {
        return especieRemetente;
    }

    public void setEspecieRemetente(String especieRemetente) {
        this.especieRemetente = especieRemetente;
    }

    public String getnPedido() {
        return nPedido;
    }

    public void setnPedido(String nPedido) {
        this.nPedido = nPedido;
    }

    public String getVeiculoRemetente() {
        return veiculoRemetente;
    }

    public void setVeiculoRemetente(String veiculoRemetente) {
        this.veiculoRemetente = veiculoRemetente;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getDimensoes() {
        return dimensoes;
    }

    public void setDimensoes(String dimensoes) {
        this.dimensoes = dimensoes;
    }

    public String getnONU() {
        return nONU;
    }

    public void setnONU(String nONU) {
        this.nONU = nONU;
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

    public String getDestinoDestinatario() {
        return destinoDestinatario;
    }

    public void setDestinoDestinatario(String destinoDestinatario) {
        this.destinoDestinatario = destinoDestinatario;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPlacaDestinatario() {
        return placaDestinatario;
    }

    public void setPlacaDestinatario(String placaDestinatario) {
        this.placaDestinatario = placaDestinatario;
    }

    public String getSemiReboqueDestinatario() {
        return semiReboqueDestinatario;
    }

    public void setSemiReboqueDestinatario(String semiReboqueDestinatario) {
        this.semiReboqueDestinatario = semiReboqueDestinatario;
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

    public String getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(String instrucoes) {
        this.instrucoes = instrucoes;
    }

    public String getDataColetaEfetuada() {
        return dataColetaEfetuada;
    }

    public void setDataColetaEfetuada(String dataColetaEfetuada) {
        this.dataColetaEfetuada = dataColetaEfetuada;
    }

    public String getHoraColetaEfetuada() {
        return horaColetaEfetuada;
    }

    public void setHoraColetaEfetuada(String horaColetaEfetuada) {
        this.horaColetaEfetuada = horaColetaEfetuada;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
