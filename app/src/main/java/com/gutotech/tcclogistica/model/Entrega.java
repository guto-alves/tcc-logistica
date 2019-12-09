package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Entrega {
    private String id;
    private Nota nota;
    private Funcionario motorista;
    private String nomeMotorista;
    private String data;
    private String hora;

    private Status status;
    private ResultadoViagem resultadoViagem;

    public Entrega() {
    }

    public Entrega(String id, Nota nota, Funcionario motorista, String nomeMotorista, String data, String hora, Status status, ResultadoViagem resultadoViagem) {
        this.id = id;
        this.nota = nota;
        this.motorista = motorista;
        this.nomeMotorista = nomeMotorista;
        this.data = data;
        this.hora = hora;
        this.status = status;
        this.resultadoViagem = resultadoViagem;
    }

    public void salvar() {
        DatabaseReference entregaReference = ConfigFirebase.getDatabase()
                .child("entrega")
                .child(id);

        entregaReference.setValue(this);
    }

    public void excluir() {
        DatabaseReference entregaReference = ConfigFirebase.getDatabase()
                .child("entrega")
                .child(id);

        entregaReference.removeValue();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public Funcionario getMotorista() {
        return motorista;
    }

    public void setMotorista(Funcionario motorista) {
        this.motorista = motorista;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
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
