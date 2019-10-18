package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Entrega {
    private String id;
<<<<<<< HEAD
    private Nota nota;
    private Funcionario motorista;
    private String data;
    private String hora;
=======
    private Nota produto;
    private Motorista motorista;
    private Endereco endereco;
    private Hora horarioSaida;
    private Hora horarioChegada;
>>>>>>> 259a14608e5ffa6225fbb0fef545d89535731aae

    private enum Status {ENTREGUE, PEDENTE, DEVOLUCAO}

    private Status status;

    public Entrega() {
    }

    public Entrega(String id, Nota nota, Funcionario motorista, String data, String hora, Status status) {
        this.id = id;
        this.nota = nota;
        this.motorista = motorista;
        this.data = data;
        this.hora = hora;
        this.status = status;
    }

    public void salvar() {
        DatabaseReference entregaReference = ConfigFirebase.getDatabase()
                .child("entregas")
                .child(id);

        entregaReference.setValue(this);
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
}
