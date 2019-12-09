package com.gutotech.tcclogistica.view.adm.ui.veiculo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Veiculo;

import es.dmoral.toasty.Toasty;

public class VeiculoDialog extends Dialog {
    private Veiculo veiculo;

    private EditText placaEditText, marcaEditText, modeloEditText, anoEditText, corEditText;

    public VeiculoDialog(@NonNull Context context, final Veiculo veiculo) {
        super(context);
        setContentView(R.layout.dialog_veiculo);
        setCancelable(false);

        this.veiculo = veiculo;

        placaEditText = findViewById(R.id.placaEditText);
        marcaEditText = findViewById(R.id.marcaEditText);
        modeloEditText = findViewById(R.id.modeloEditText);
        anoEditText = findViewById(R.id.anoEditText);
        corEditText = findViewById(R.id.corEditText);

        adicionarMascaras();
        setInformations();
        changeMode(false);

        ImageButton fecharButton = findViewById(R.id.fecharButton);
        fecharButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ImageButton excluirButton = findViewById(R.id.excluirButton);
        excluirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("EXCLUIR FUNCIONÁRIO");
                alert.setMessage("Tem certeza que deseja excluir o veículo " + veiculo.getModelo() + " ?");
                alert.create();
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        veiculo.excluir();
                        dismiss();
                    }
                });
                alert.setNegativeButton("Cancelar", null);
                alert.show();
            }
        });

        final ImageButton updateImageButton = findViewById(R.id.updateImageButton);
        final ImageButton modeEditImageButton = findViewById(R.id.modoEditImageButton);

        modeEditImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMode(true);
                modeEditImageButton.setVisibility(View.GONE);
                updateImageButton.setVisibility(View.VISIBLE);
            }
        });

        updateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee();
                Toasty.success(getContext(), "Veículo atualizado", Toasty.LENGTH_SHORT, true).show();

                changeMode(false);
                updateImageButton.setVisibility(View.GONE);
                modeEditImageButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setInformations() {
        placaEditText.setText(veiculo.getPlaca());
        marcaEditText.setText(veiculo.getPlaca());
        modeloEditText.setText(veiculo.getPlaca());
        anoEditText.setText(veiculo.getPlaca());
        corEditText.setText(veiculo.getPlaca());
    }

    private void updateEmployee() {
        veiculo.setPlaca(placaEditText.getText().toString());
        veiculo.setMarca(marcaEditText.getText().toString());
        veiculo.setModelo(modeloEditText.getText().toString());
        veiculo.setAno(anoEditText.getText().toString());
        veiculo.setCor(corEditText.getText().toString());

        veiculo.salvar();
    }

    private void adicionarMascaras() {
        anoEditText.addTextChangedListener(new MaskTextWatcher(anoEditText, new SimpleMaskFormatter("NNNN")));
        placaEditText.addTextChangedListener(new MaskTextWatcher(placaEditText, new SimpleMaskFormatter("LLL-NNNN")));
    }

    private void changeMode(boolean mode) {
        placaEditText.setEnabled(mode);
        marcaEditText.setEnabled(mode);
        modeloEditText.setEnabled(mode);
        anoEditText.setEnabled(mode);
        corEditText.setEnabled(mode);
    }
}
