package com.gutotech.tcclogistica.view.motorista.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.helper.DateCustom;
import com.gutotech.tcclogistica.model.Entrega;

public class FinalizacaoEntregaDialog extends Dialog {
    private EditText horarioChegadaEditText;
    private EditText horarioSaidaEditText;

    private EditText outrosMotivosEditText;

    public FinalizacaoEntregaDialog(@NonNull Context context, final Entrega entrega) {
        super(context);
        setContentView(R.layout.dialog_finalizar_entrega);

        horarioChegadaEditText = findViewById(R.id.horarioChegadaEditText);
        horarioSaidaEditText = findViewById(R.id.horarioSaidaEditText);
        outrosMotivosEditText = findViewById(R.id.outrosMotivosEditText);

        horarioSaidaEditText.setText(DateCustom.getHorario());

        RadioGroup motivosRadioGroup = findViewById(R.id.motivosRadioGroup);
        motivosRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.outrosRadioButton) {
                    outrosMotivosEditText.setVisibility(View.VISIBLE);
                } else {
                    outrosMotivosEditText.setVisibility(View.GONE);
                }
            }
        });

        final Switch entregaRealizadaSwitch = findViewById(R.id.entregaRealizadaSwitch);
        entregaRealizadaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LinearLayout linearLayout = findViewById(R.id.motivoNaoEntregaLinear);

                if (!isChecked)
                    linearLayout.setVisibility(View.VISIBLE);
                else linearLayout.setVisibility(View.GONE);
            }
        });

        Button enviarButton = findViewById(R.id.enviarButton);
        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entregaRealizadaSwitch.isActivated()) {
                    entrega.setStatus(Entrega.Status.REALIZADA);
                    entrega.salvar();
                } else {
                    Toast.makeText(getContext(), "Test", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button cancelarButton = findViewById(R.id.cancelarButton);
        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        addMasks();
    }

    private void addMasks() {
        horarioChegadaEditText.addTextChangedListener(new MaskTextWatcher(horarioChegadaEditText, new SimpleMaskFormatter("NN:NN:NN")));
        horarioSaidaEditText.addTextChangedListener(new MaskTextWatcher(horarioSaidaEditText, new SimpleMaskFormatter("NN:NN:NN")));
    }
}
