package com.gutotech.tcclogistica.view.roteirista.ui.coleta;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.TextRecognizer;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.helper.Listener;
import com.gutotech.tcclogistica.model.Coleta;
import com.gutotech.tcclogistica.model.Destinatario;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.Produto;
import com.gutotech.tcclogistica.model.Remetente;
import com.gutotech.tcclogistica.model.Veiculo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class RoteiristaColetaNovaFragment extends Fragment {
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

    private EditText rgMotoristaEditText;

    private EditText observacoesEditText, instrucoesEditText;

    private EditText dataColetaEfetuadaEditText, horaColetaEfetuadaEditText;

    private Coleta coleta = new Coleta();

    private ArrayAdapter motoristasArrayAdapter;

    private List<Funcionario> motoristasList = new ArrayList<>();
    private List<String> nomesMotoristasList = new ArrayList<>();

    private DatabaseReference motoristasReference;
    private Query motoristasQuery;
    private ValueEventListener motoristasListener;

    private TextRecognizer textRecognizer;
    private final int REQUEST_IMAGE_CAPTURE = 7;

    private Dialog processingDialog;

    public RoteiristaColetaNovaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_coletas_nova, container, false);

        numeroColetaEditText = root.findViewById(R.id.numeroColetaEditText);
        coletarEmEditText = root.findViewById(R.id.coletarEmEditText);
        dataEmissaoEditText = root.findViewById(R.id.dataEmissaoEditText);

        nomeRemetenteEditText = root.findViewById(R.id.nomeRemetenteEditText);
        enderecoRemetenteEditText = root.findViewById(R.id.enderecoRemetenteEditText);
        referenciaRemetenteEditText = root.findViewById(R.id.referenciaRemetenteEditText);
        bairroRemetenteEditText = root.findViewById(R.id.bairroRemetenteEditText);
        cidadeRemetenteEditText = root.findViewById(R.id.cidadeRemetenteEditText);
        cepRemetenteEditText = root.findViewById(R.id.cepRemetenteEditText);

        contatoRemetenteEditText = root.findViewById(R.id.contatoRemetenteEditText);
        telefoneRemetenteEditText = root.findViewById(R.id.telefoneRemetenteEditText);
        especieRemetenteEditText = root.findViewById(R.id.especieRemetenteEditText);
        nPedidoEditText = root.findViewById(R.id.numeroPedidoEditText);
        veiculoRemetenteEditText = root.findViewById(R.id.veiculoRemetenteEditText);

        nomeProdutoEditText = root.findViewById(R.id.nomeProdutoEditText);
        pesoEditText = root.findViewById(R.id.pesoEditText);
        dimensoesEditText = root.findViewById(R.id.dimensoesEditText);
        nONUEditText = root.findViewById(R.id.nONUEditText);

        nomeDestinatarioEditText = root.findViewById(R.id.nomeDestinatarioEditText);
        enderecoDestinatarioEditText = root.findViewById(R.id.enderecoDestinatarioEditText);
        destinoDestinatarioEditText = root.findViewById(R.id.destinoDestinatarioEditText);
        siteEditText = root.findViewById(R.id.siteDestinatarioEditText);
        placaDestinatarioEditText = root.findViewById(R.id.placaDestinatarioEditText);
        semiReboqueDestinatarioEditText = root.findViewById(R.id.semiReboqueDestinatarioEditText);

        Spinner motoristaSpinner = root.findViewById(R.id.motoristaSpinner);
        rgMotoristaEditText = root.findViewById(R.id.rgMotoristaEditText);

        observacoesEditText = root.findViewById(R.id.observacoesEditText);
        instrucoesEditText = root.findViewById(R.id.instrucoesEditText);

        dataColetaEfetuadaEditText = root.findViewById(R.id.dataColetaEfetuadaEditText);
        horaColetaEfetuadaEditText = root.findViewById(R.id.horaColetaEfetuadaEditText);

        adicionarMascaras();

        motoristasReference = ConfigFirebase.getDatabase().child("funcionario");

        motoristasArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, nomesMotoristasList);
        motoristasArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motoristaSpinner.setAdapter(motoristasArrayAdapter);
        motoristaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coleta.setMotorista(motoristasList.get(position));
                rgMotoristaEditText.setText(coleta.getMotorista().getRg());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ImageButton textRecognizerImageButton = root.findViewById(R.id.textRecognizerImageButton);
        textRecognizerImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_chooser);
                dialog.show();

                LinearLayout cameraLayout = dialog.findViewById(R.id.cameraLinear);
                cameraLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1);
                    }
                });

                LinearLayout galeraLayout = dialog.findViewById(R.id.galeriaLinear);
                galeraLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent1 = new Intent();
                        intent1.setType("image/*");
                        intent1.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent1, "Selecione uma imagem"), 2);
                    }
                });
            }
        });

        textRecognizer = new TextRecognizer(listener);

        processingDialog = new Dialog(getActivity());
        processingDialog.setContentView(R.layout.dialog_carregando);
        processingDialog.setCancelable(false);

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

    private final Listener listener = new Listener() {
        @Override
        public void callback(String text) {
            instrucoesEditText.setText(text);

            processingDialog.dismiss();
        }
    };

    private final View.OnClickListener salvarButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isValidFields(numeroColetaEditText.getText().toString())) {
                coleta.setId(UUID.randomUUID().toString());

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

                coleta.salvar();

                limparCampos();
                Toasty.success(getActivity(), "Coleta cadastrada com sucesso!", Toasty.LENGTH_SHORT, true).show();
            }
        }
    };

    private void adicionarMascaras() {
        coletarEmEditText.addTextChangedListener(new MaskTextWatcher(coletarEmEditText, new SimpleMaskFormatter("NN/NN/NN até NN:NN:NN hr")));
        dataEmissaoEditText.addTextChangedListener(new MaskTextWatcher(dataEmissaoEditText, new SimpleMaskFormatter("NN/NN/NN NN:NN:NN")));

        cepRemetenteEditText.addTextChangedListener(new MaskTextWatcher(cepRemetenteEditText, new SimpleMaskFormatter("(NNNNN-NNN")));
        telefoneRemetenteEditText.addTextChangedListener(new MaskTextWatcher(telefoneRemetenteEditText, new SimpleMaskFormatter("(NN) NNNN-NNNN")));

        placaDestinatarioEditText.addTextChangedListener(new MaskTextWatcher(placaDestinatarioEditText, new SimpleMaskFormatter("UUU-NNNN")));

        rgMotoristaEditText.addTextChangedListener(new MaskTextWatcher(rgMotoristaEditText, new SimpleMaskFormatter("NN.NNN.NNN-N")));

        dataColetaEfetuadaEditText.addTextChangedListener(new MaskTextWatcher(dataColetaEfetuadaEditText, new SimpleMaskFormatter("NN/NN/NN")));
        horaColetaEfetuadaEditText.addTextChangedListener(new MaskTextWatcher(horaColetaEfetuadaEditText, new SimpleMaskFormatter("NN:NN:NN")));
    }

    private boolean isValidFields(String numeroColeta) {
        boolean valid = true;

        if (numeroColeta.isEmpty()) {
            numeroColetaEditText.setError("Este campo é obrigatório");
            valid = false;
        }

        return valid;
    }

    private void limparCampos() {
        numeroColetaEditText.setText("");
        coletarEmEditText.setText("");
        dataEmissaoEditText.setText("");

        nomeRemetenteEditText.setText("");
        enderecoRemetenteEditText.setText("");
        referenciaRemetenteEditText.setText("");
        bairroRemetenteEditText.setText("");
        cidadeRemetenteEditText.setText("");
        cepRemetenteEditText.setText("");

        contatoRemetenteEditText.setText("");
        telefoneRemetenteEditText.setText("");
        especieRemetenteEditText.setText("");
        nPedidoEditText.setText("");
        veiculoRemetenteEditText.setText("");

        nomeProdutoEditText.setText("");
        pesoEditText.setText("");
        dimensoesEditText.setText("");
        nONUEditText.setText("");

        nomeDestinatarioEditText.setText("");
        enderecoDestinatarioEditText.setText("");
        destinoDestinatarioEditText.setText("");
        siteEditText.setText("");
        placaDestinatarioEditText.setText("");
        semiReboqueDestinatarioEditText.setText("");

        observacoesEditText.setText("");
        instrucoesEditText.setText("");

        dataColetaEfetuadaEditText.setText("");
        horaColetaEfetuadaEditText.setText("");
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = null;

            try {
                if (requestCode == 1)
                    bitmap = (Bitmap) data.getExtras().get("data");
                else if (requestCode == 2) {
                    Uri uri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            processingDialog.show();
            textRecognizer.detect(bitmap);
        }
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
