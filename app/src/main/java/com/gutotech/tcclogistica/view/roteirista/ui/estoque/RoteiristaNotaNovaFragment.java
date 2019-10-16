package com.gutotech.tcclogistica.view.roteirista.ui.estoque;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gutotech.tcclogistica.R;

import java.util.UUID;

public class RoteiristaNotaNovaFragment extends Fragment {
    private EditText dataEmissaoEditText;
    private EditText naturezaOperacaoEditText;

    // campos remetente
    private EditText nomeRemetenteEditText, enderecoRemetenteEditText, referenciaEditText,
            bairroEditText, cidadeEditText, cepEditText, contatoEditText, telefoneEditText, nPedidoEditText, veiculoEditText;

    // campos produto
    private EditText produtoEditText, pesoEditText, dimensoesEditText, nONUEditText;

    // campos transportador
    private EditText nomeTransportadorEditText, fretePorContaEditText, codANTTEditText, placaVeiculoEditText, cnpjEditText,
            enderecoTransportadorEditText, municipioTransportadorEditText, ufEditText, ieEditText,
            quantidadeTransportadorEditText, especieEditText, marcaEditText, numeroTransportadorEditText, pesoBrutoEdiText, pesoLiquidoEdiText;

    private EditText motoristaEditText, rgEditText;

    private EditText dadosAdicionaisEditText;

    private EditText dataEfetuada, horaEfetuada;

    public RoteiristaNotaNovaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_nota_nova, container, false);


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
