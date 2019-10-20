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
import com.gutotech.tcclogistica.model.Coleta;
import com.gutotech.tcclogistica.model.Destinatario;
import com.gutotech.tcclogistica.model.Produto;
import com.gutotech.tcclogistica.model.Remetente;
import com.gutotech.tcclogistica.model.Veiculo;

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

    private Coleta coleta = new Coleta();

    public RoteiristaColetaNovaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_coletas_nova, container, false);

        nomeRemetenteEditText = root.findViewById(R.id.nomeRemetenteEditText);
        rgEditText = root.findViewById(R.id.rgEditText);
        contatoEditText = root.findViewById(R.id.nomeRemetenteEditText);
        telefoneEditText = root.findViewById(R.id.nomeRemetenteEditText);
        nPedidoEditText = root.findViewById(R.id.nomeRemetenteEditText);
        horaEfetuada = root.findViewById(R.id.nomeRemetenteEditText);
        motoristaEditText = root.findViewById(R.id.nomeRemetenteEditText);

        veiculoEditText = root.findViewById(R.id.nomeRemetenteEditText);
        observacoesEditText = root.findViewById(R.id.nomeRemetenteEditText);
        instrucoesEditText = root.findViewById(R.id.nomeRemetenteEditText);
        dataEfetuada = root.findViewById(R.id.nomeRemetenteEditText);
        placaEditText = root.findViewById(R.id.nomeRemetenteEditText);
        rgEditText = root.findViewById(R.id.nomeRemetenteEditText);

        Button salvarButton = root.findViewById(R.id.salvarButton);
        salvarButton.setOnClickListener(salvarButtonListener);

        Button limparButton = root.findViewById(R.id.limparButton);
        limparButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCampos();
            }
        });

        return root;
    }

    private final View.OnClickListener salvarButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Remetente remetente = new Remetente();
            Produto produto = new Produto();
            Destinatario destinatario = new Destinatario();

            remetente.setRemetente(nomeRemetenteEditText.getText().toString());
            remetente.setContato(contatoEditText.getText().toString());
            remetente.setNumeroPedido(nPedidoEditText.getText().toString());
            remetente.setTelefone(telefoneEditText.getText().toString());

            destinatario.setNome(nomeDestinatarioEditText.getText().toString());
            destinatario.setPlaca(placaEditText.getText().toString());

            coleta.setId(UUID.randomUUID().toString());
            coleta.setDestinatario(destinatario);
            coleta.setProduto(produto);
            coleta.setRemetente(remetente);
            coleta.salvar();
        }
    };

    private void limparCampos() {
        nomeRemetenteEditText.setText("");
        rgEditText.setText("");
        contatoEditText.setText("");
        telefoneEditText.setText("");
        nPedidoEditText.setText("");
        horaEfetuada.setText("");
        motoristaEditText.setText("");

        veiculoEditText.setText("");
        observacoesEditText.setText("");
        instrucoesEditText.setText("");
        dataEfetuada.setText("");
        placaEditText.setText("");
        rgEditText.setText("");
    }

}
