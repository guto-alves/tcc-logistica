package com.gutotech.tcclogistica.view.roteirista.ui.entrega;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.helper.DateCustom;
import com.gutotech.tcclogistica.model.Entrega;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.Nota;
import com.gutotech.tcclogistica.model.ResultadoViagem;
import com.gutotech.tcclogistica.model.Status;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class RoteiristaEntregaNovaFragment extends Fragment {
    private Entrega entrega = new Entrega();

    private EditText dataEditText, horaEditText;

    private ArrayAdapter notasArrayAdapter;
    private List<Nota> notaList = new LinkedList<>();
    private List<String> numerosNotasList = new LinkedList<>();

    private ArrayAdapter motoristasArrayAdapter;
    private List<Funcionario> motoristasList = new LinkedList<>();
    private List<String> nomesMotoristasList = new LinkedList<>();

    private DatabaseReference notasReference;
    private ValueEventListener notasListener;
    private Query notasQuery;

    private DatabaseReference motoristasReference;
    private Query motoristasQuery;
    private ValueEventListener motoristasListener;

    public RoteiristaEntregaNovaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_entrega_nova, container, false);

        Spinner notaSpinner = root.findViewById(R.id.notaSpinner);
        Spinner motoristaSpinner = root.findViewById(R.id.motoristaSpinner);
        dataEditText = root.findViewById(R.id.dataEditText);
        final CalendarView calendarView = root.findViewById(R.id.calendarView);
        horaEditText = root.findViewById(R.id.horaEditText);

        adicionarMascaras();

        horaEditText.setText(DateCustom.getHorario());

        notasReference = ConfigFirebase.getDatabase().child("nota");
        motoristasReference = ConfigFirebase.getDatabase().child("funcionario");

        notasArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, numerosNotasList);
        notasArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notaSpinner.setAdapter(notasArrayAdapter);
        notaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entrega.setNota(notaList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        motoristasArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, nomesMotoristasList);
        motoristasArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motoristaSpinner.setAdapter(motoristasArrayAdapter);
        motoristaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Funcionario motorista = motoristasList.get(position);
                entrega.setMotorista(motorista);
                entrega.setNomeMotorista(motorista.getNome());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
            String data = dataEditText.getText().toString();
            String hora = horaEditText.getText().toString();

            if (entrega.getNota() != null) {
                if (isValidFields(data, hora)) {
                    entrega.setId(UUID.randomUUID().toString());
                    entrega.setData(data);
                    entrega.setHora(hora);
                    entrega.setStatus(Status.PENDENTE);
                    entrega.setResultadoViagem(new ResultadoViagem());

                    entrega.getNota().setEstoque(false);
                    entrega.getNota().salvar();

                    entrega.salvar();
                    entrega = new Entrega();
                    entrega.setMotorista(motoristasList.get(0));
                    entrega.setNomeMotorista(entrega.getMotorista().getNome());

                    limparCampos();

                    Toasty.success(getActivity(), "Entrega cadastrada com sucesso!", Toasty.LENGTH_SHORT, true).show();
                }
            } else
                Toasty.error(getActivity(), "Nenhuma nota associada a essa entrega!", Toasty.LENGTH_SHORT, true).show();
        }
    };

    private void limparCampos() {
        dataEditText.setText("");
        horaEditText.setText("");
    }

    private void adicionarMascaras() {
        dataEditText.addTextChangedListener(new MaskTextWatcher(dataEditText, new SimpleMaskFormatter("NN/NN/NNNN")));
        horaEditText.addTextChangedListener(new MaskTextWatcher(horaEditText, new SimpleMaskFormatter("NN:NN")));
    }

    private boolean isValidFields(String data, String hora) {
        boolean valid = true;

        if (data.isEmpty()) {
            dataEditText.setError("Este campo é obrigatório");
            valid = false;
        }

        if (hora.isEmpty()) {
            horaEditText.setError("Este campo é obrigatório");
            valid = false;
        }

        return valid;
    }

    private void getNotas() {
        notasQuery = notasReference.orderByChild("estoque").equalTo(true);

        notasListener = notasQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notaList.clear();
                numerosNotasList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Nota nota = data.getValue(Nota.class);
                    notaList.add(nota);
                    numerosNotasList.add(nota.getNumero());
                }

                if (notaList.size() != 0) {
                    Nota nota = notaList.get(0);
                    entrega.setNota(nota);
                } else
                    entrega.setNota(null);

                notasArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
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
        getNotas();
    }

    @Override
    public void onStop() {
        super.onStop();

        motoristasQuery.removeEventListener(motoristasListener);
        notasQuery.removeEventListener(notasListener);
    }
}
