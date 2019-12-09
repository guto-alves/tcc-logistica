package com.gutotech.tcclogistica.view.adm.ui.veiculo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Veiculo;
import com.gutotech.tcclogistica.view.ProcessingDialog;

import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class VeiculoNovoFragment extends Fragment {
    private EditText placaEditText, marcaEditText, modeloEditText, anoEditText, corEditText;

    private ProcessingDialog processingDialog;

    public VeiculoNovoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_veiculo_novo, container, false);

        placaEditText = root.findViewById(R.id.placaEditText);
        marcaEditText = root.findViewById(R.id.marcaEditText);
        modeloEditText = root.findViewById(R.id.modeloEditText);
        anoEditText = root.findViewById(R.id.anoEditText);
        corEditText = root.findViewById(R.id.corEditText);

        processingDialog = new ProcessingDialog(getActivity());

        adicionarMascaras();

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
            if (placaEditText.getText().toString().trim().isEmpty()) {
                placaEditText.setError("Este campo é obrigatório");
                return;
            }

            if (modeloEditText.getText().toString().trim().isEmpty()) {
                modeloEditText.setError("Este campo é obrigatório");
                return;
            }

            processingDialog.show();

            salvarVeiculo();

            limparCampos();
            processingDialog.dismiss();

            Toasty.success(getActivity(), "Veículo cadastrado com sucesso", Toasty.LENGTH_SHORT, true).show();
        }
    };

    private void salvarVeiculo() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(UUID.randomUUID().toString());
        veiculo.setPlaca(placaEditText.getText().toString());
        veiculo.setMarca(marcaEditText.getText().toString());
        veiculo.setModelo(modeloEditText.getText().toString());
        veiculo.setCor(corEditText.getText().toString());
        veiculo.setAno(anoEditText.getText().toString());
        veiculo.setAlocado(false);
        veiculo.salvar();
    }

    private void limparCampos() {
        placaEditText.setText("");
        marcaEditText.setText("");
        modeloEditText.setText("");
        corEditText.setText("");
        anoEditText.setText("");
    }

    private void adicionarMascaras() {
        anoEditText.addTextChangedListener(new MaskTextWatcher(anoEditText, new SimpleMaskFormatter("NNNN")));
        placaEditText.addTextChangedListener(new MaskTextWatcher(placaEditText, new SimpleMaskFormatter("LLL-NNNN")));
    }
}
