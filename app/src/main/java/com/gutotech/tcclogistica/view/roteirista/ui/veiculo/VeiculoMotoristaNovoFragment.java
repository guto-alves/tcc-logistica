package com.gutotech.tcclogistica.view.roteirista.ui.veiculo;

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
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.Veiculo;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class VeiculoMotoristaNovoFragment extends Fragment {
    private Funcionario motorista;
    private Veiculo veiculo;

    private ArrayAdapter veiculosArrayAdapter;
    private List<Veiculo> veiculosList = new LinkedList<>();
    private List<String> nomesVeiculosList = new LinkedList<>();

    private ArrayAdapter motoristasArrayAdapter;
    private List<Funcionario> motoristasList = new LinkedList<>();
    private List<String> nomesMotoristasList = new LinkedList<>();

    private DatabaseReference veiculosReference;
    private ValueEventListener veiculosListener;
    private Query veiculosQuery;

    private DatabaseReference motoristasReference;
    private Query motoristasQuery;
    private ValueEventListener motoristasListener;


    public VeiculoMotoristaNovoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_veiculo_motorista_novo, container, false);

        Spinner veiculoSpinner = root.findViewById(R.id.veiculoSpinner);
        Spinner motoristaSpinner = root.findViewById(R.id.motoristaSpinner);

        veiculosReference = ConfigFirebase.getDatabase().child("veiculo");
        motoristasReference = ConfigFirebase.getDatabase().child("funcionario");

        veiculosArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, nomesVeiculosList);
        veiculosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        veiculoSpinner.setAdapter(veiculosArrayAdapter);
        veiculoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                veiculo = veiculosList.get(position);
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
                motorista = motoristasList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button salvarButton = root.findViewById(R.id.salvarButton);
        salvarButton.setOnClickListener(salvarButtonListener);

        return root;
    }

    private final View.OnClickListener salvarButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (motorista != null && veiculo != null) {
                veiculo.setAlocado(true);
                veiculo.salvar();
                motorista.setVeiculo(veiculo);
                motorista.salvar();

                Toasty.success(getActivity(), "Veículo alocado ao " + motorista.getNome() + " com sucesso!", Toasty.LENGTH_SHORT, true).show();

            } else
                Toasty.warning(getActivity(), "Não há motorista e/ou veículo disponível(eis)", Toasty.LENGTH_SHORT, true).show();
        }
    };

    private void getVeiculos() {
        veiculosQuery = veiculosReference.orderByChild("alocado").equalTo(false);

        veiculosListener = veiculosQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                veiculosList.clear();
                nomesVeiculosList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Veiculo veiculo = data.getValue(Veiculo.class);
                    veiculosList.add(veiculo);
                    nomesVeiculosList.add(veiculo.getModelo() + " - " + veiculo.getPlaca());
                }

                if (veiculosList.size() > 0)
                    veiculo = veiculosList.get(0);
                else veiculo = null;

                veiculosArrayAdapter.notifyDataSetChanged();
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
                    if (!funcionario.getVeiculo().isAlocado()) {
                        motoristasList.add(funcionario);
                        nomesMotoristasList.add(funcionario.getNome());
                    }
                }

                if (motoristasList.size() > 0)
                    motorista = motoristasList.get(0);
                else motorista = null;

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
        getVeiculos();
    }

    @Override
    public void onStop() {
        super.onStop();

        motoristasQuery.removeEventListener(motoristasListener);
        veiculosQuery.removeEventListener(veiculosListener);
    }

}
