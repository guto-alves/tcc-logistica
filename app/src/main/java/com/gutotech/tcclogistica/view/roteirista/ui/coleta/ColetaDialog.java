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

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ColetaDialog extends Dialog {
    private Coleta coleta;

    private EditText numeroColetaEditText;
    private EditText coletarEmEditText;
    private EditText dataEmissaoEditText;

    //  remetente
    private EditText nomeRemetenteEditText, enderecoRemetenteEditText, referenciaRemetenteEditText,
            bairroRemetenteEditText, cidadeRemetenteEditText, cepRemetenteEditText,
            contatoRemetenteEditText, telefoneRemetenteEditText, especieRemetenteEditText, nPedidoEditText, veiculoRemetenteEditText;

    //  produto
    private EditText nomeProdutoEditText, pesoEditText, dimensoesEditText, nONUEditText;

    //  destinatario
    private EditText nomeDestinatarioEditText, enderecoDestinatarioEditText, destinoDestinatarioEditText,
            siteEditText, placaDestinatarioEditText, semiReboqueDestinatarioEditText;

    private Spinner motoristaSpinner;
    private EditText rgMotoristaEditText;

    private EditText observacoesEditText, instrucoesEditText;

    private EditText dataColetaEfetuadaEditText, horaColetaEfetuadaEditText;

    private List<Funcionario> motoristasList = new ArrayList<>();
    private ArrayAdapter motoristasArrayAdapter;
    private List<String> nomesMotoristasList = new ArrayList<>();

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
        referenciaRemetenteEditText = findViewById(R.id.referenciaRemetenteEditText);
        bairroRemetenteEditText = findViewById(R.id.bairroRemetenteEditText);
        cidadeRemetenteEditText = findViewById(R.id.cidadeRemetenteEditText);
        cepRemetenteEditText = findViewById(R.id.cepRemetenteEditText);

        contatoRemetenteEditText = findViewById(R.id.contatoRemetenteEditText);
        telefoneRemetenteEditText = findViewById(R.id.telefoneRemetenteEditText);
        especieRemetenteEditText = findViewById(R.id.especieRemetenteEditText);
        nPedidoEditText = findViewById(R.id.numeroPedidoEditText);
        veiculoRemetenteEditText = findViewById(R.id.veiculoRemetenteEditText);

        nomeProdutoEditText = findViewById(R.id.nomeProdutoEditText);
        pesoEditText = findViewById(R.id.pesoEditText);
        dimensoesEditText = findViewById(R.id.dimensoesEditText);
        nONUEditText = findViewById(R.id.nONUEditText);

        nomeDestinatarioEditText = findViewById(R.id.nomeDestinatarioEditText);
        enderecoDestinatarioEditText = findViewById(R.id.enderecoDestinatarioEditText);
        destinoDestinatarioEditText = findViewById(R.id.destinoDestinatarioEditText);
        siteEditText = findViewById(R.id.siteDestinatarioEditText);
        placaDestinatarioEditText = findViewById(R.id.placaDestinatarioEditText);
        semiReboqueDestinatarioEditText = findViewById(R.id.semiReboqueDestinatarioEditText);

        motoristaSpinner = findViewById(R.id.motoristaSpinner);
        rgMotoristaEditText = findViewById(R.id.rgMotoristaEditText);

        observacoesEditText = findViewById(R.id.observacoesEditText);
        instrucoesEditText = findViewById(R.id.instrucoesEditText);

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
        coletarEmEditText.setText(coleta.getColetarEm());
        dataEmissaoEditText.setText(coleta.getDataEmissao());

        nomeRemetenteEditText.setText(coleta.getNomeRemetente());
        enderecoRemetenteEditText.setText(coleta.getEnderecoRemetente());
        referenciaRemetenteEditText.setText(coleta.getReferenciaRemetente());
        bairroRemetenteEditText.setText(coleta.getBairroRemetente());
        cidadeRemetenteEditText.setText(coleta.getCidadeRemetente());
        cepRemetenteEditText.setText(coleta.getCepRemetente());

        contatoRemetenteEditText.setText(coleta.getContatoRemetente());
        telefoneRemetenteEditText.setText(coleta.getTelefoneRemetente());
        especieRemetenteEditText.setText(coleta.getEspecieRemetente());

        nPedidoEditText.setText(coleta.getnPedido());
        veiculoRemetenteEditText.setText(coleta.getVeiculoRemetente());

        nomeProdutoEditText.setText(coleta.getNomeProduto());
        pesoEditText.setText(coleta.getPeso());
        dimensoesEditText.setText(coleta.getDimensoes());
        nONUEditText.setText(coleta.getnONU());

        nomeDestinatarioEditText.setText(coleta.getNomeDestinatario());
        enderecoDestinatarioEditText.setText(coleta.getEnderecoDestinatario());
        destinoDestinatarioEditText.setText(coleta.getDestinoDestinatario());
        siteEditText.setText(coleta.getSite());
        placaDestinatarioEditText.setText(coleta.getPlacaDestinatario());
        semiReboqueDestinatarioEditText.setText(coleta.getSemiReboqueDestinatario());

        rgMotoristaEditText.setText(coleta.getMotorista().getRg());

        observacoesEditText.setText(coleta.getObservacoes());
        instrucoesEditText.setText(coleta.getInstrucoes());

        dataColetaEfetuadaEditText.setText(coleta.getDataColetaEfetuada());
        horaColetaEfetuadaEditText.setText(coleta.getHoraColetaEfetuada());
    }

    private void atualizarColeta() {
        if (isValidFields(numeroColetaEditText.getText().toString())) {
            coleta.setNumero(numeroColetaEditText.getText().toString());
            coleta.setColetarEm(coletarEmEditText.getText().toString());
            coleta.setDataEmissao(dataEmissaoEditText.getText().toString());

            coleta.setNomeRemetente(nomeRemetenteEditText.getText().toString());
            coleta.setEnderecoRemetente(enderecoRemetenteEditText.getText().toString());
            coleta.setReferenciaRemetente(referenciaRemetenteEditText.getText().toString());
            coleta.setBairroRemetente(bairroRemetenteEditText.getText().toString());
            coleta.setCidadeRemetente(cidadeRemetenteEditText.getText().toString());
            coleta.setCepRemetente(cepRemetenteEditText.getText().toString());
            coleta.setContatoRemetente(contatoRemetenteEditText.getText().toString());
            coleta.setTelefoneRemetente(telefoneRemetenteEditText.getText().toString());
            coleta.setEspecieRemetente(especieRemetenteEditText.getText().toString());
            coleta.setnPedido(especieRemetenteEditText.getText().toString());
            coleta.setVeiculoRemetente(veiculoRemetenteEditText.getText().toString());

            coleta.setNomeProduto(nomeProdutoEditText.getText().toString());
            coleta.setPeso(pesoEditText.getText().toString());
            coleta.setDimensoes(dimensoesEditText.getText().toString());
            coleta.setnONU(nONUEditText.getText().toString());

            coleta.setNomeDestinatario(nomeDestinatarioEditText.getText().toString());
            coleta.setEnderecoDestinatario(enderecoDestinatarioEditText.getText().toString());
            coleta.setDestinoDestinatario(destinoDestinatarioEditText.getText().toString());
            coleta.setSite(siteEditText.getText().toString());
            coleta.setPlacaDestinatario(placaDestinatarioEditText.getText().toString());

            coleta.setObservacoes(observacoesEditText.getText().toString());
            coleta.setInstrucoes(instrucoesEditText.getText().toString());

            coleta.setDataColetaEfetuada(dataColetaEfetuadaEditText.getText().toString());
            coleta.setHoraColetaEfetuada(horaColetaEfetuadaEditText.getText().toString());
            coleta.setStatus(Coleta.Status.PENDENTE);
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
        referenciaRemetenteEditText.setEnabled(mode);
        bairroRemetenteEditText.setEnabled(mode);
        cidadeRemetenteEditText.setEnabled(mode);
        cepRemetenteEditText.setEnabled(mode);

        contatoRemetenteEditText.setEnabled(mode);
        telefoneRemetenteEditText.setEnabled(mode);
        especieRemetenteEditText.setEnabled(mode);
        nPedidoEditText.setEnabled(mode);
        veiculoRemetenteEditText.setEnabled(mode);

        nomeProdutoEditText.setEnabled(mode);
        pesoEditText.setEnabled(mode);
        dimensoesEditText.setEnabled(mode);
        nONUEditText.setEnabled(mode);

        nomeDestinatarioEditText.setEnabled(mode);
        enderecoDestinatarioEditText.setEnabled(mode);
        destinoDestinatarioEditText.setEnabled(mode);
        siteEditText.setEnabled(mode);
        placaDestinatarioEditText.setEnabled(mode);
        semiReboqueDestinatarioEditText.setEnabled(mode);

        motoristaSpinner.setEnabled(mode);

        observacoesEditText.setEnabled(mode);
        instrucoesEditText.setEnabled(mode);

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

        placaDestinatarioEditText.addTextChangedListener(new MaskTextWatcher(placaDestinatarioEditText, new SimpleMaskFormatter("UUU-NNNN")));

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
