package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Veiculo {
    private String id;
    private String placa;
    private String marca;
    private String modelo;
    private String cor;
    private String ano;

    public Veiculo() {
        this("", "", "", "", "", "");
    }

    public Veiculo(String id, String placa, String marca, String modelo, String cor, String ano) {
        this.id = id;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.ano = ano;
    }

    public void salvar() {
        DatabaseReference veiculoReference = ConfigFirebase.getDatabase()
                .child("veiculo")
                .child(id);

        veiculoReference.setValue(this);
    }

    public void excluir() {
        DatabaseReference veiculoReference = ConfigFirebase.getDatabase()
                .child("veiculo")
                .child(id);

        veiculoReference.removeValue();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }
}
