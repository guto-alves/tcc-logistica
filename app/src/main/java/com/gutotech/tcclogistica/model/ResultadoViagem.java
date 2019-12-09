package com.gutotech.tcclogistica.model;

public class ResultadoViagem {
    private String data;
    private String horarioChegada;
    private String horarioSaida;
    private String aconteceu;

    public ResultadoViagem() {
        this("", "", "", "");
    }

    public ResultadoViagem(String data, String horarioChegada, String horarioSaida, String aconteceu) {
        this.data = data;
        this.horarioChegada = horarioChegada;
        this.horarioSaida = horarioSaida;
        this.aconteceu = aconteceu;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorarioChegada() {
        return horarioChegada;
    }

    public void setHorarioChegada(String horarioChegada) {
        this.horarioChegada = horarioChegada;
    }

    public String getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(String horarioSaida) {
        this.horarioSaida = horarioSaida;
    }

    public String getAconteceu() {
        return aconteceu;
    }

    public void setAconteceu(String aconteceu) {
        this.aconteceu = aconteceu;
    }
}
