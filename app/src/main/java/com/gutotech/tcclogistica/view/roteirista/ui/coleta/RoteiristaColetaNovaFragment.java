package com.gutotech.tcclogistica.view.roteirista.ui.coleta;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.helper.DateCustom;
import com.gutotech.tcclogistica.helper.TextRecognizer;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.model.Coleta;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.ResultadoViagem;
import com.gutotech.tcclogistica.model.Status;
import com.gutotech.tcclogistica.view.OpenCameraOrGalleryDialogFragment;
import com.gutotech.tcclogistica.view.ProcessingDialog;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class RoteiristaColetaNovaFragment extends Fragment {
    private Coleta coleta = new Coleta();

    private EditText numeroColetaEditText;
    private EditText dataEditText;
    private EditText horaEditText;

    //  remetente
    private EditText nomeRemetenteEditText, enderecoRemetenteEditText,
            bairroRemetenteEditText, cidadeRemetenteEditText, cepRemetenteEditText,
            contatoRemetenteEditText, telefoneRemetenteEditText, numeroPedidoEditText;

    //  destinatario
    private EditText nomeDestinatarioEditText, enderecoDestinatarioEditText,
            bairroDestinatarioEditText, cidadeDestinatarioEditText, cepDestinatarioEditText,
            contatoDestinatarioEditText, telefoneDestinatarioEditText;

    private EditText rgMotoristaEditText;

    private EditText observacoesEditText;

    private List<Funcionario> motoristasList = new LinkedList<>();
    private ArrayAdapter motoristasArrayAdapter;
    private List<String> nomesMotoristasList = new LinkedList<>();

    private DatabaseReference motoristasReference;
    private Query motoristasQuery;
    private ValueEventListener motoristasListener;

    private TextRecognizer textRecognizer;

    private ProcessingDialog processingDialog;

    public RoteiristaColetaNovaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_coletas_nova, container, false);

        numeroColetaEditText = root.findViewById(R.id.numeroColetaEditText);
        dataEditText = root.findViewById(R.id.dataEditText);
        final CalendarView calendarView = root.findViewById(R.id.calendarView);
        horaEditText = root.findViewById(R.id.horaEditText);

        nomeRemetenteEditText = root.findViewById(R.id.nomeRemetenteEditText);
        enderecoRemetenteEditText = root.findViewById(R.id.enderecoRemetenteEditText);
        bairroRemetenteEditText = root.findViewById(R.id.bairroRemetenteEditText);
        cidadeRemetenteEditText = root.findViewById(R.id.cidadeRemetenteEditText);
        cepRemetenteEditText = root.findViewById(R.id.cepRemetenteEditText);
        contatoRemetenteEditText = root.findViewById(R.id.contatoRemetenteEditText);
        telefoneRemetenteEditText = root.findViewById(R.id.telefoneRemetenteEditText);
        numeroPedidoEditText = root.findViewById(R.id.numeroPedidoEditText);

        nomeDestinatarioEditText = root.findViewById(R.id.nomeDestinatarioEditText);
        enderecoDestinatarioEditText = root.findViewById(R.id.enderecoDestinatarioEditText);
        bairroDestinatarioEditText = root.findViewById(R.id.bairroDestinatarioEditText);
        cidadeDestinatarioEditText = root.findViewById(R.id.cidadeDestinatarioEditText);
        cepDestinatarioEditText = root.findViewById(R.id.cepDestinatarioEditText);
        contatoDestinatarioEditText = root.findViewById(R.id.contatoDestinatarioEditText);
        telefoneDestinatarioEditText = root.findViewById(R.id.telefoneDestinatarioEditText);

        Spinner motoristaSpinner = root.findViewById(R.id.motoristaSpinner);
        rgMotoristaEditText = root.findViewById(R.id.rgMotoristaEditText);
        observacoesEditText = root.findViewById(R.id.observacoesEditText);

        adicionarMascaras();

        dataEditText.setText(DateCustom.getData());
        dataEditText.addTextChangedListener(new MaskTextWatcher(dataEditText, new SimpleMaskFormatter("NN/NN/NNNN")));
        dataEditText.setFocusable(false);
        dataEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setVisibility(View.VISIBLE);
            }
        });

        calendarView.setVisibility(View.GONE);
        calendarView.setDate(System.currentTimeMillis());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String data = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                dataEditText.setText(data);
                dataEditText.setError(null);
                calendarView.setVisibility(View.GONE);
            }
        });

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

        Button salvarButton = root.findViewById(R.id.salvarButton);
        salvarButton.setOnClickListener(salvarButtonListener);

        Button limparButton = root.findViewById(R.id.limparButton);
        limparButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCampos();
            }
        });

        ImageButton textRecognizerImageButton = root.findViewById(R.id.textRecognizerImageButton);
        textRecognizerImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCameraOrGalleryDialogFragment openCameraOrGalleryDialogFragment = new OpenCameraOrGalleryDialogFragment();
                openCameraOrGalleryDialogFragment.setListener(openCameraOrGalleryListener);
                openCameraOrGalleryDialogFragment.show(getActivity().getSupportFragmentManager(), "dialog");
            }
        });

        motoristasReference = ConfigFirebase.getDatabase().child("funcionario");

        textRecognizer = new TextRecognizer(textRecognizerListener);

        processingDialog = new ProcessingDialog(getActivity());

        return root;
    }

    private final OpenCameraOrGalleryDialogFragment.Listener openCameraOrGalleryListener = new OpenCameraOrGalleryDialogFragment.Listener() {

        @Override
        public void onBitmapResult(Bitmap bitmap) {
            processingDialog.show();
            textRecognizer.detect(bitmap);
        }
    };

    private final TextRecognizer.Listener textRecognizerListener = new TextRecognizer.Listener() {
        @Override
        public void onTextResult(String text) {
            String[] tokens = text.split("\\s+");

            for (int i = 0; i < tokens.length; i++)
                Log.i("teste", String.format("%dº = %s", i, tokens[i]));

            numeroColetaEditText.setText(tokens[5]);
            dataEditText.setText(String.format("%s até %s", tokens[7].replace("Coletar:", ""), tokens[9]));
            horaEditText.setText(tokens[13] + " " + tokens[14] + " hr");

            nomeRemetenteEditText.setText(text.substring(text.indexOf("Remetente"), text.indexOf("Endereço")));
            enderecoRemetenteEditText.setText(text.substring(text.indexOf("Endereço"), text.indexOf("Referência")));
            contatoRemetenteEditText.setText(text.substring(text.indexOf("Contato"), text.indexOf("Telefone:")));
            telefoneRemetenteEditText.setText(text.substring(text.indexOf("Telefone: "), text.indexOf("Especie:")));
            nomeDestinatarioEditText.setText(tokens[0]);

            processingDialog.dismiss();
        }
    };

    private final View.OnClickListener salvarButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isValidFields(numeroColetaEditText.getText().toString())) {
                coleta.setId(UUID.randomUUID().toString());

                coleta.setNumero(numeroColetaEditText.getText().toString());
                coleta.setData(dataEditText.getText().toString());
                coleta.setHora(horaEditText.getText().toString());

                coleta.setNomeRemetente(nomeRemetenteEditText.getText().toString());
                coleta.setEnderecoRemetente(enderecoRemetenteEditText.getText().toString());
                coleta.setBairroRemetente(bairroRemetenteEditText.getText().toString());
                coleta.setCidadeRemetente(cidadeRemetenteEditText.getText().toString());
                coleta.setCepRemetente(cepRemetenteEditText.getText().toString());
                coleta.setContatoRemetente(contatoRemetenteEditText.getText().toString());
                coleta.setTelefoneRemetente(telefoneRemetenteEditText.getText().toString());
                coleta.setNumeroPedido(numeroPedidoEditText.getText().toString());

                coleta.setNomeDestinatario(nomeDestinatarioEditText.getText().toString());
                coleta.setEnderecoDestinatario(enderecoDestinatarioEditText.getText().toString());
                coleta.setBairroDestinatario(bairroDestinatarioEditText.getText().toString());
                coleta.setCidadeDestinatario(cidadeDestinatarioEditText.getText().toString());
                coleta.setCepDestinatario(cepDestinatarioEditText.getText().toString());
                coleta.setContatoRemetente(contatoDestinatarioEditText.getText().toString());
                coleta.setTelefoneRemetente(telefoneDestinatarioEditText.getText().toString());

                coleta.setObservacoes(observacoesEditText.getText().toString());

                coleta.setResultadoViagem(new ResultadoViagem());

                coleta.setStatus(Status.PENDENTE);
                coleta.salvar();

                limparCampos();
                Toasty.success(getActivity(), "Coleta salva com sucesso!", Toasty.LENGTH_SHORT, true).show();
            }
        }
    };

    private void adicionarMascaras() {
        dataEditText.addTextChangedListener(new MaskTextWatcher(dataEditText, new SimpleMaskFormatter("NN/NN/NNNN")));
        horaEditText.addTextChangedListener(new MaskTextWatcher(horaEditText, new SimpleMaskFormatter("NN:NN")));

        cepRemetenteEditText.addTextChangedListener(new MaskTextWatcher(cepRemetenteEditText, new SimpleMaskFormatter("NN.NNN-NNN")));
        telefoneRemetenteEditText.addTextChangedListener(new MaskTextWatcher(telefoneRemetenteEditText, new SimpleMaskFormatter("(NN) NNNN-NNNN")));

        cepDestinatarioEditText.addTextChangedListener(new MaskTextWatcher(cepDestinatarioEditText, new SimpleMaskFormatter("NN.NNN-NNN")));
        telefoneDestinatarioEditText.addTextChangedListener(new MaskTextWatcher(telefoneDestinatarioEditText, new SimpleMaskFormatter("(NN) NNNN-NNNN")));

        rgMotoristaEditText.addTextChangedListener(new MaskTextWatcher(rgMotoristaEditText, new SimpleMaskFormatter("NN.NNN.NNN-N")));
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
        dataEditText.setText("");
        horaEditText.setText("");

        nomeRemetenteEditText.setText("");
        enderecoRemetenteEditText.setText("");
        bairroRemetenteEditText.setText("");
        cidadeRemetenteEditText.setText("");
        cepRemetenteEditText.setText("");
        contatoRemetenteEditText.setText("");
        telefoneRemetenteEditText.setText("");
        numeroPedidoEditText.setText("");

        nomeDestinatarioEditText.setText("");
        enderecoDestinatarioEditText.setText("");
        bairroDestinatarioEditText.setText("");
        cidadeDestinatarioEditText.setText("");
        cepDestinatarioEditText.setText("");
        contatoDestinatarioEditText.setText("");
        telefoneDestinatarioEditText.setText("");

        observacoesEditText.setText("");
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
