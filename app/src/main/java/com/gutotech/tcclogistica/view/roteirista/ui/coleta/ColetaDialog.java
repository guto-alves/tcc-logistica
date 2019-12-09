package com.gutotech.tcclogistica.view.roteirista.ui.coleta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.model.Coleta;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.Status;

import java.util.LinkedList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ColetaDialog extends Dialog {
    private Coleta coleta;

    private EditText numeroColetaEditText;
    private EditText coletarEmEditText;
    private EditText dataEmissaoEditText;

    //  remetente
    private EditText nomeRemetenteEditText, enderecoRemetenteEditText,
            bairroRemetenteEditText, cidadeRemetenteEditText, cepRemetenteEditText,
            contatoRemetenteEditText, telefoneRemetenteEditText, numeroPedidoEditText;

    //  destinatario
    private EditText nomeDestinatarioEditText, enderecoDestinatarioEditText,
            bairroDestinatarioEditText, cidadeDestinatarioEditText, cepDestinatarioEditText,
            contatoDestinatarioEditText, telefoneDestinatarioEditText;

    private Spinner motoristaSpinner;
    private EditText rgMotoristaEditText;

    private EditText observacoesEditText;

    private EditText dataColetaEfetuadaEditText, horaColetaEfetuadaEditText;

    private List<Funcionario> motoristasList = new LinkedList<>();
    private ArrayAdapter motoristasArrayAdapter;
    private List<String> nomesMotoristasList = new LinkedList<>();

    private DatabaseReference motoristasReference;
    private Query motoristasQuery;
    private ValueEventListener motoristasListener;

    public ColetaDialog(@NonNull Context context, Coleta coleta) {
        super(context);
        setContentView(R.layout.dialog_coleta);
        setCancelable(false);

        this.coleta = coleta;

        numeroColetaEditText = findViewById(R.id.numeroColetaEditText);
        coletarEmEditText = findViewById(R.id.coletarEmEditText);
        dataEmissaoEditText = findViewById(R.id.dataEmissaoEditText);

        nomeRemetenteEditText = findViewById(R.id.nomeRemetenteEditText);
        enderecoRemetenteEditText = findViewById(R.id.enderecoRemetenteEditText);
        bairroRemetenteEditText = findViewById(R.id.bairroRemetenteEditText);
        cidadeRemetenteEditText = findViewById(R.id.cidadeRemetenteEditText);
        cepRemetenteEditText = findViewById(R.id.cepRemetenteEditText);
        contatoRemetenteEditText = findViewById(R.id.contatoRemetenteEditText);
        telefoneRemetenteEditText = findViewById(R.id.telefoneRemetenteEditText);
        numeroPedidoEditText = findViewById(R.id.numeroPedidoEditText);

        nomeDestinatarioEditText = findViewById(R.id.nomeDestinatarioEditText);
        enderecoDestinatarioEditText = findViewById(R.id.enderecoDestinatarioEditText);
        bairroDestinatarioEditText = findViewById(R.id.bairroDestinatarioEditText);
        cidadeDestinatarioEditText = findViewById(R.id.cidadeDestinatarioEditText);
        cepDestinatarioEditText = findViewById(R.id.cepDestinatarioEditText);
        contatoDestinatarioEditText = findViewById(R.id.contatoDestinatarioEditText);
        telefoneDestinatarioEditText = findViewById(R.id.telefoneDestinatarioEditText);

        dataColetaEfetuadaEditText = findViewById(R.id.dataColetaEfetuadaEditText);
        horaColetaEfetuadaEditText = findViewById(R.id.horaColetaEfetuadaEditText);

        motoristaSpinner = findViewById(R.id.motoristaSpinner);
        rgMotoristaEditText = findViewById(R.id.rgMotoristaEditText);

        observacoesEditText = findViewById(R.id.observacoesEditText);

        dataColetaEfetuadaEditText = findViewById(R.id.dataColetaEfetuadaEditText);
        horaColetaEfetuadaEditText = findViewById(R.id.horaColetaEfetuadaEditText);

        motoristasArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, nomesMotoristasList);
        motoristasArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motoristaSpinner.setAdapter(motoristasArrayAdapter);
        motoristaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ColetaDialog.this.coleta.setMotorista(motoristasList.get(position));
                rgMotoristaEditText.setText(ColetaDialog.this.coleta.getMotorista().getRg());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        adicionarMascaras();

        ImageButton fecharButton = findViewById(R.id.fecharButton);
        final ImageButton updateImageButton = findViewById(R.id.updateImageButton);
        final ImageButton modeEditImageButton = findViewById(R.id.modoEditImageButton);
        ImageButton excluirButton = findViewById(R.id.excluirButton);

        fecharButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        excluirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("EXCLUIR ORDEM DE COLETA");
                alert.setMessage("Tem certeza que deseja excluir a coleta?");
                alert.create();
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ColetaDialog.this.coleta.excluir();
                        dismiss();
                    }
                });
                alert.setNegativeButton("Não", null);
                alert.show();
            }
        });

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
                atualizarColeta();
                Toasty.success(getContext(), "Coleta atualizada", Toasty.LENGTH_SHORT, true).show();

                changeMode(false);
                updateImageButton.setVisibility(View.GONE);
                modeEditImageButton.setVisibility(View.VISIBLE);
            }
        });

        setInformations();
        changeMode(false);

        motoristasReference = ConfigFirebase.getDatabase().child("funcionario");
    }

    private void setInformations() {
        numeroColetaEditText.setText(coleta.getNumero());
        coletarEmEditText.setText(coleta.getData());
        dataEmissaoEditText.setText(coleta.getHora());

        nomeRemetenteEditText.setText(coleta.getNomeRemetente());
        enderecoRemetenteEditText.setText(coleta.getEnderecoRemetente());
        bairroRemetenteEditText.setText(coleta.getBairroRemetente());
        cidadeRemetenteEditText.setText(coleta.getCidadeRemetente());
        cepRemetenteEditText.setText(coleta.getCepRemetente());
        contatoRemetenteEditText.setText(coleta.getContatoRemetente());
        telefoneRemetenteEditText.setText(coleta.getTelefoneRemetente());
        numeroPedidoEditText.setText(coleta.getNumeroPedido());

        nomeDestinatarioEditText.setText(coleta.getNomeDestinatario());
        enderecoDestinatarioEditText.setText(coleta.getEnderecoDestinatario());
        bairroDestinatarioEditText.setText(coleta.getBairroDestinatario());
        cidadeDestinatarioEditText.setText(coleta.getCidadeDestinatario());
        cepDestinatarioEditText.setText(coleta.getCepDestinatario());
        contatoDestinatarioEditText.setText(coleta.getContatoDestinatario());
        telefoneDestinatarioEditText.setText(coleta.getTelefoneDestinatario());

        rgMotoristaEditText.setText(coleta.getMotorista().getRg());

        observacoesEditText.setText(coleta.getObservacoes());
    }

    private void atualizarColeta() {
        if (isValidFields(numeroColetaEditText.getText().toString())) {
            coleta.setNumero(numeroColetaEditText.getText().toString());
            coleta.setData(coletarEmEditText.getText().toString());
            coleta.setHora(dataEmissaoEditText.getText().toString());

            coleta.setNomeRemetente(nomeRemetenteEditText.getText().toString());
            coleta.setEnderecoRemetente(enderecoRemetenteEditText.getText().toString());
            coleta.setBairroRemetente(bairroRemetenteEditText.getText().toString());
            coleta.setCidadeRemetente(cidadeRemetenteEditText.getText().toString());
            coleta.setCepRemetente(cepRemetenteEditText.getText().toString());
            coleta.setContatoRemetente(contatoRemetenteEditText.getText().toString());
            coleta.setTelefoneRemetente(telefoneRemetenteEditText.getText().toString());

            coleta.setNomeDestinatario(nomeDestinatarioEditText.getText().toString());
            coleta.setEnderecoDestinatario(enderecoDestinatarioEditText.getText().toString());

            coleta.setObservacoes(observacoesEditText.getText().toString());

            coleta.setStatus(Status.PENDENTE);
            coleta.salvar();
            Toasty.success(getContext(), "Coleta atualizada", Toasty.LENGTH_SHORT, true).show();
        } else
            numeroColetaEditText.setError("Este campo é obrigatório");
    }

    private void changeMode(boolean mode) {
        numeroColetaEditText.setEnabled(mode);
        coletarEmEditText.setEnabled(mode);
        dataEmissaoEditText.setEnabled(mode);

        nomeRemetenteEditText.setEnabled(mode);
        enderecoRemetenteEditText.setEnabled(mode);
        bairroRemetenteEditText.setEnabled(mode);
        cidadeRemetenteEditText.setEnabled(mode);
        cepRemetenteEditText.setEnabled(mode);
        contatoRemetenteEditText.setEnabled(mode);
        telefoneRemetenteEditText.setEnabled(mode);
        numeroPedidoEditText.setEnabled(mode);

        nomeDestinatarioEditText.setEnabled(mode);
        enderecoDestinatarioEditText.setEnabled(mode);
        bairroDestinatarioEditText.setEnabled(mode);
        cidadeDestinatarioEditText.setEnabled(mode);
        cepDestinatarioEditText.setEnabled(mode);
        contatoDestinatarioEditText.setEnabled(mode);
        telefoneDestinatarioEditText.setEnabled(mode);

        motoristaSpinner.setEnabled(mode);

        observacoesEditText.setEnabled(mode);

        dataColetaEfetuadaEditText.setEnabled(mode);
        horaColetaEfetuadaEditText.setEnabled(mode);
    }

    private boolean isValidFields(String numeroColeta) {
        boolean valid = true;

        if (numeroColeta.isEmpty())
            valid = false;

        return valid;
    }

    private void adicionarMascaras() {
        coletarEmEditText.addTextChangedListener(new MaskTextWatcher(coletarEmEditText, new SimpleMaskFormatter("NN/NN/NNNN até NN:NN hr")));
        dataEmissaoEditText.addTextChangedListener(new MaskTextWatcher(dataEmissaoEditText, new SimpleMaskFormatter("NN/NN/NNNN NN:NN")));

        cepRemetenteEditText.addTextChangedListener(new MaskTextWatcher(cepRemetenteEditText, new SimpleMaskFormatter("NN.NNN-NNN")));
        telefoneRemetenteEditText.addTextChangedListener(new MaskTextWatcher(telefoneRemetenteEditText, new SimpleMaskFormatter("(NN) NNNN-NNNN")));

        cepDestinatarioEditText.addTextChangedListener(new MaskTextWatcher(cepDestinatarioEditText, new SimpleMaskFormatter("NN.NNN-NNN")));
        telefoneDestinatarioEditText.addTextChangedListener(new MaskTextWatcher(telefoneDestinatarioEditText, new SimpleMaskFormatter("(NN) NNNN-NNNN")));

        rgMotoristaEditText.addTextChangedListener(new MaskTextWatcher(rgMotoristaEditText, new SimpleMaskFormatter("NN.NNN.NNN-N")));

        dataColetaEfetuadaEditText.addTextChangedListener(new MaskTextWatcher(dataColetaEfetuadaEditText, new SimpleMaskFormatter("NN/NN/NNNN")));
        horaColetaEfetuadaEditText.addTextChangedListener(new MaskTextWatcher(horaColetaEfetuadaEditText, new SimpleMaskFormatter("NN:NN:NN")));
    }

    private void getMotoristas() {
        motoristasQuery = motoristasReference.orderByChild("cargo").equalTo(Funcionario.MOTORISTA);

        motoristasListener = motoristasQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                motoristasList.clear();
                nomesMotoristasList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Funcionario funcionario = data.getValue(Funcionario.class);
                    motoristasList.add(funcionario);
                    nomesMotoristasList.add(funcionario.getNome());
                }

                motoristasArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getMotoristas();
    }

    @Override
    public void onStop() {
        super.onStop();
        motoristasQuery.removeEventListener(motoristasListener);
    }
}
