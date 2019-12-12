package com.gutotech.tcclogistica.view.motorista.ui.coleta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.gutotech.tcclogistica.model.Coleta;
import com.gutotech.tcclogistica.model.FuncionarioOn;
import com.gutotech.tcclogistica.model.Status;
import com.gutotech.tcclogistica.view.adapter.ColetasPendentesAdapter;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class MotoristaColetasPendentesFragment extends Fragment {
    private List<Coleta> coletasList = new LinkedList<>();
    private ColetasPendentesAdapter coletasAdapter;

    private Query coletasPorDataQuery;
    private ValueEventListener coletasListener;

    private EditText dataEditText;
    private TextView totalTextView;
    private TextView statusTextView;
    private ProgressBar progressBar;

    public MotoristaColetasPendentesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_motorista_entregas_pendentes, container, false);

        dataEditText = root.findViewById(R.id.dataEditText);
        final CalendarView calendarView = root.findViewById(R.id.calendarView);
        final CheckBox todasCheckBox = root.findViewById(R.id.todasCheckBox);
        statusTextView = root.findViewById(R.id.statusTextView);
        totalTextView = root.findViewById(R.id.totalTextView);
        progressBar = root.findViewById(R.id.progressBar);

        dataEditText.setText(DateCustom.getData());
        dataEditText.addTextChangedListener(new MaskTextWatcher(dataEditText, new SimpleMaskFormatter("NN/NN/NNNN")));
        dataEditText.setFocusable(false);
        dataEditText.setText(DateCustom.getData());
        dataEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setVisibility(View.VISIBLE);
                todasCheckBox.setEnabled(false);
            }
        });

        calendarView.setVisibility(View.GONE);
        calendarView.setDate(System.currentTimeMillis());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String data = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                dataEditText.setText(data);
                calendarView.setVisibility(View.GONE);
                buscarColetasPorData(data);
                todasCheckBox.setEnabled(true);
            }
        });

        todasCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataEditText.setEnabled(false);
                    buscarColetasPorData("");
                } else {
                    dataEditText.setEnabled(true);
                    buscarColetasPorData(dataEditText.getText().toString());
                }
            }
        });

        RecyclerView entregasRecyclerView = root.findViewById(R.id.entregasPendentesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        entregasRecyclerView.setLayoutManager(layoutManager);
        entregasRecyclerView.setHasFixedSize(true);
        coletasAdapter = new ColetasPendentesAdapter(getActivity(), coletasList);
        entregasRecyclerView.setAdapter(coletasAdapter);

        return root;
    }

    private void buscarColetasPorData(String data) {
        DatabaseReference entregasReference = ConfigFirebase.getDatabase().child("coleta");

        coletasPorDataQuery = entregasReference.orderByChild("data").startAt(data).endAt(data + "\uf8ff");

        coletasListener = coletasPorDataQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                coletasList.clear();

                statusTextView.setText("Buscando coletas ..");
                statusTextView.setVisibility(View.VISIBLE);

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Coleta coleta = data.getValue(Coleta.class);

                    if (coleta.getMotorista().getLogin().getUser().equals(FuncionarioOn.funcionario.getLogin().getUser())) {
                        if (coleta.getStatus() == Status.PENDENTE)
                            coletasList.add(coleta);
                    }
                }

                int totalColetas = coletasList.size();
                totalTextView.setText(String.format(Locale.getDefault(), "Total: %d", totalColetas));

                if (totalColetas == 0)
                    statusTextView.setText("Nenhuma coleta encontrada.");
                else
                    statusTextView.setVisibility(View.GONE);

                progressBar.setVisibility(View.GONE);
                coletasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        buscarColetasPorData(DateCustom.getData());
    }

    @Override
    public void onStop() {
        super.onStop();
        coletasPorDataQuery.removeEventListener(coletasListener);
    }

}
