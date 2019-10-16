package com.gutotech.tcclogistica.view.roteirista.ui.entregas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.model.Entrega;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.Motorista;
import com.gutotech.tcclogistica.model.Nota;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoteiristaEntregaNovaFragment extends Fragment {
    private Spinner motoristaSpinner, notaSpinner;
    private ArrayAdapter arrayAdapterMotoristas, arrayAdapterNotas;

    private EditText dataEditText, horaEditText;

    private Entrega entrega = new Entrega();

    private List<Funcionario> motoristasList = new ArrayList<>();
    private List<String> nomesMotoristasList = new ArrayList<>();

    private List<Nota> notaList = new ArrayList<>();
    private List<String> nomesNotasList = new ArrayList<>();


    private DatabaseReference notasReference;
    private ValueEventListener notasListener;

    private DatabaseReference motoristasReference;
    private ValueEventListener motoristasListener;

    public RoteiristaEntregaNovaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_entrega_nova, container, false);

        //notaSpinner = root.findViewById(R.id.notaSpinner);
        //motoristaSpinner = root.findViewById(R.id.motoristaSpinner);
        //dataEditText = root.findViewById(R.id.dataEditText);
        //horaEditText = root.findViewById(R.id.horaEditText);

        motoristasReference = ConfigFirebase.getDatabase().child("funcionario");
        notasReference = ConfigFirebase.getDatabase().child("nota");

        arrayAdapterMotoristas = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, nomesMotoristasList);
        arrayAdapterMotoristas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motoristaSpinner.setAdapter(arrayAdapterMotoristas);
        motoristaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entrega.setMotorista(motoristasList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        arrayAdapterNotas = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, nomesNotasList);
        arrayAdapterNotas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notaSpinner.setAdapter(arrayAdapterNotas);
        notaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entrega.setNota(notaList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button salvarButton = root.findViewById(R.id.salvarButton);
        salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = dataEditText.getText().toString();
                String hora = horaEditText.getText().toString();

                if (isValidFields(data, hora)) {
                    entrega.setData(data);
                    entrega.setHora(hora);

                    entrega.setId(UUID.randomUUID().toString());
                    entrega.salvar();
                }
            }
        });

        Button limparButton = root.findViewById(R.id.limparButton);
        limparButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataEditText.setText("");
                horaEditText.setText("");
            }
        });

        return root;
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
        notasListener = notasReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Nota nota = dataSnapshot.getValue(Nota.class);

                notaList.add(nota);
                arrayAdapterNotas.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getMotoristas() {
        motoristasListener = motoristasReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Funcionario funcionario = dataSnapshot.getValue(Funcionario.class);

                if (funcionario.getCargo().equals(Funcionario.MOTORISTA)) {
                    motoristasList.add(funcionario);
                    nomesMotoristasList.add(funcionario.getNome());
                    arrayAdapterMotoristas.notifyDataSetChanged();
                }
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

        motoristasReference.removeEventListener(motoristasListener);
        notasReference.removeEventListener(notasListener);
    }
}
