package com.gutotech.tcclogistica.view.roteirista.ui.coleta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.gutotech.tcclogistica.R;

import java.util.UUID;

public class RoteiristaColetaNovaFragment extends Fragment {
    // campos remetente
    private EditText nomeRemetenteEditText, enderecoRemetenteEditText, referenciaEditText,
            bairroEditText, cidadeEditText, cepEditText, contatoEditText, telefoneEditText, especieEditText, nPedidoEditText, veiculoEditText;

    // campos produto
    private EditText produtoEditText, pesoEditText, dimensoesEditText, nONUEditText;

    // campos destinatario
    private EditText nomeDestinatarioEditText, enderecoDestinatarioEditText, destinoEditText,
            siteEditText, placaEditText, semiRoboqueEditText;

    private EditText motoristaEditText, rgEditText;

    private EditText observacoesEditText, instrucoesEditText;

    private EditText dataEfetuada, horaEfetuada;

    public RoteiristaColetaNovaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_coletas_nova, container, false);


        Button salvarButton = root.findViewById(R.id.salvarButton);
        salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button limparButton = root.findViewById(R.id.limparButton);
        limparButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }

}