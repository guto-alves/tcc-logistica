package com.gutotech.tcclogistica.model;

import java.security.SecureRandom;
import java.util.Locale;

public class GeradorLogin {
    private String nome;
    private String tipo;

    public GeradorLogin(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getUser() {
        return String.format(Locale.getDefault(), "%s%d.%s", nome, new SecureRandom().nextInt(100), tipo);
    }
}
