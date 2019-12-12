package com.gutotech.tcclogistica.view.motorista.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.helper.DateCustom;
import com.gutotech.tcclogistica.model.Entrega;
import com.gutotech.tcclogistica.model.ResultadoViagem;
import com.gutotech.tcclogistica.model.Status;
import com.gutotech.tcclogistica.view.EscolhaSimOuNaoDialog;
import com.tapadoo.alerter.Alerter;

import es.dmoral.toasty.Toasty;

public class FinalizacaoEntregaDialog extends Dialog {
    private EditText horarioChegadaEditText;
    private EditText horarioSaidaEditText;

    private ImageButton openChooseImageButton;

    private RadioGroup motivosRadioGroup;
    private EditText outrosMotivosEditText;

    private Status status;

    public FinalizacaoEntregaDialog(@NonNull final Context context, final Entrega entrega) {
        super(context);
        setContentView(R.layout.dialog_finalizacao_entrega);

        status = Status.PENDENTE;

        horarioChegadaEditText = findViewById(R.id.horarioChegadaEditText);
        horarioSaidaEditText = findViewById(R.id.horarioSaidaEditText);
        outrosMotivosEditText = findViewById(R.id.outrosMotivosEditText);

        addMasks();

        horarioSaidaEditText.setText(DateCustom.getHorario());

        openChooseImageButton = findViewById(R.id.openChoiceDialogImageButton);
        openChooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EscolhaSimOuNaoDialog escolhaSimOuNaoDialog = new EscolhaSimOuNaoDialog(context, choiceListener);
                escolhaSimOuNaoDialog.show();
            }
        });

        motivosRadioGroup = findViewById(R.id.motivosRadioGroup);
        motivosRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.outrosRadioButton)
                    outrosMotivosEditText.setVisibility(View.VISIBLE);
                else
                    outrosMotivosEditText.setVisibility(View.GONE);

            }
        });

        Button enviarButton = findViewById(R.id.enviarButton);
        View.OnClickListener enviarClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!camposValidos())
                    return;

                if (status == Status.PENDENTE) {
                    Toasty.warning(getContext(), "Responda se a entrega foi realizada ou não", Toasty.LENGTH_SHORT, true).show();
                    return;
                }

                ResultadoViagem resultadoViagem = new ResultadoViagem();
                resultadoViagem.setData(DateCustom.getData());
                resultadoViagem.setHorarioChegada(horarioChegadaEditText.getText().toString());
                resultadoViagem.setHorarioSaida(horarioSaidaEditText.getText().toString());

                if (status == Status.NAO_REALIZADA) {
                    switch (motivosRadioGroup.getCheckedRadioButtonId()) {
                        case R.id.radioButton1:
                            resultadoViagem.setAconteceu("Mudança de endereço");
                            break;
                        case R.id.radioButton2:
                            resultadoViagem.setAconteceu("Desacordo com pedido");
                            break;
                        case R.id.radioButton3:
                            resultadoViagem.setAconteceu("Cliente não se encontra");
                            break;
                        case R.id.outrosRadioButton:
                            String motivo = outrosMotivosEditText.getText().toString().trim();

                            if (!motivo.isEmpty()) {
                                resultadoViagem.setAconteceu(motivo);
                                outrosMotivosEditText.setError(null);
                            } else {
                                outrosMotivosEditText.setError("Por favor, digite o motivo");
                                return;
                            }
                            break;
                    }

                    entrega.getNota().setEstoque(true);
                }

                entrega.setResultadoViagem(resultadoViagem);
                entrega.getNota().salvar();

                entrega.setStatus(status);
                entrega.salvar();

                Alerter.create((Activity) context)
                        .setTitle("Entrega finalizada com sucesso")
                        .setBackgroundColor(R.color.colorGreen)
                        .setIcon(R.drawable.ic_done_24dp)
                        .show();
                dismiss();
            }
        };
        enviarButton.setOnClickListener(enviarClickListener);

        Button cancelarButton = findViewById(R.id.cancelarButton);
        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    private boolean camposValidos() {
        if (horarioChegadaEditText.getText().toString().trim().isEmpty()) {
            horarioChegadaEditText.setError("Digite o horário que você chegou no cliente");
            horarioChegadaEditText.requestFocus();
            return false;
        } else
            horarioChegadaEditText.setError(null);

        if (horarioSaidaEditText.getText().toString().trim().isEmpty()) {
            horarioSaidaEditText.setError("Digite o horário que você saiu do cliente");
            horarioSaidaEditText.requestFocus();
            return false;
        } else
            horarioSaidaEditText.setError(null);

        return true;
    }

    private final EscolhaSimOuNaoDialog.ChoiceListener choiceListener = new EscolhaSimOuNaoDialog.ChoiceListener() {
        @Override
        public void onChoice(int choice) {
            openChooseImageButton.setImageResource(choice);

            LinearLayout linearLayout = findViewById(R.id.motivoNaoEntregaLinear);

            if (R.drawable.ic_thumb_up_24dp == choice) {
                linearLayout.setVisibility(View.GONE);
                status = Status.REALIZADA;
            } else {
                linearLayout.setVisibility(View.VISIBLE);
                status = Status.NAO_REALIZADA;
            }
        }
    };

    private void addMasks() {
        horarioChegadaEditText.addTextChangedListener(new MaskTextWatcher(horarioChegadaEditText, new SimpleMaskFormatter("NN:NN")));
        horarioSaidaEditText.addTextChangedListener(new MaskTextWatcher(horarioSaidaEditText, new SimpleMaskFormatter("NN:NN")));
    }
}
