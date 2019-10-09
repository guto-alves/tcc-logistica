package com.gutotech.tcclogistica.model;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.tcclogistica.config.ConfigFirebase;

public class Nota {
    private int cod;
    private String nome;
    private String descricao;
    private String naturezaDaOperacao;
    private double valorTotalNotas;
    private double valorTotalProdutos;
    private int quantidade;
    private Transportador transportador;
    private String dadosAdicionais;

    public Nota() {
    }


    private void salvar() {
        DatabaseReference clienteReference = ConfigFirebase.getDatabase()
                .child("produto")
                .child(nome);

        clienteReference.setValue(this);
    }
}
