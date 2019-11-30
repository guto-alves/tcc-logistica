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
import android.widget.EditText;
import android.widget.SearchView;
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
import com.gutotech.tcclogistica.view.adapter.ColetasAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MotoristaColetasPendentesFragment extends Fragment {
    private List<Coleta> coletasList = new ArrayList<>();
    private List<Coleta> coletasDataList = new ArrayList<>();
    private List<Coleta> coletasNumeroList = new ArrayList<>();

    private ColetasAdapter coletasAdapter;

    private Query coletasNumeroQuery;
    private Query coletasDataQuery;
    private ValueEventListener coletasNumeroListener;
    private ValueEventListener coletasDataListener;

    private TextView totalTextView;
    private TextView statusColetasTextView;

    public MotoristaColetasPendentesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_motorista_coletas_pendentes, container, false);

        final CalendarView calendarView = root.findViewById(R.id.calendarView);
        final EditText dataEditText = root.findViewById(R.id.dataEditText);
        totalTextView = root.findViewById(R.id.totalTextView);
        statusColetasTextView = root.findViewById(R.id.statusTextView);
        RecyclerView coletasRecyclerView = root.findViewById(R.id.coletasRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        coletasRecyclerView.setLayoutManager(layoutManager);
        coletasRecyclerView.setHasFixedSize(true);
        coletasAdapter = new ColetasAdapter(getActivity(), coletasList);
        coletasRecyclerView.setAdapter(coletasAdapter);

        SearchView searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarColetasPorNumero(newText);
                return true;
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
                String data = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month, year);
                dataEditText.setText(data);
                calendarView.setVisibility(View.GONE);
                buscarColetasPorData(data);
            }
        });

        return root;
    }

    private void buscarColetasPorNumero(String query) {
        DatabaseReference coletasReference = ConfigFirebase.getDatabase().child("coleta");
        coletasNumeroQuery = coletasReference.orderByChild("numero").startAt(query).endAt(query + "\uf8ff");

        coletasNumeroListener = coletasNumeroQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                coletasNumeroList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Coleta coleta = data.getValue(Coleta.class);
                    if (coleta.getStatus() == Coleta.Status.PENDENTE && coleta.getMotorista().getLogin().getUser().equals(FuncionarioOn.funcionario.getLogin().getUser()))
                        coletasNumeroList.add(coleta);
                }

                matchQueries();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void buscarColetasPorData(String query) {
        DatabaseReference coletasReference = ConfigFirebase.getDatabase().child("coleta");
        coletasDataQuery = coletasReference.orderByChild("coletarEm").startAt(query).endAt(query + "\uf8ff");

        coletasDataListener = coletasDataQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                coletasDataList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Coleta coleta = data.getValue(Coleta.class);
                    if (coleta.getStatus() == Coleta.Status.PENDENTE && coleta.getMotorista().getLogin().getUser().equals(FuncionarioOn.funcionario.getLogin().getUser()))
                        coletasDataList.add(coleta);
                }

                matchQueries();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void matchQueries() {
        coletasList.clear();

        for (Coleta coletaNumero : coletasNumeroList) {
            for (Coleta coletaData : coletasDataList) {
                if (coletaNumero.getNumero().equals(coletaData.getNumero()))
                    coletasList.add(coletaData);
            }
        }

        int totalColetas = coletasList.size();
        totalTextView.setText(String.format(Locale.getDefault(), "Total: %d", totalColetas));

        if (totalColetas == 0) {
            statusColetasTextView.setText("Nenhuma coleta encontrada.");
            statusColetasTextView.setVisibility(View.VISIBLE);
        } else
            statusColetasTextView.setVisibility(View.GONE);

        coletasAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        buscarColetasPorNumero("");
        buscarColetasPorData(DateCustom.getData());
    }

    @Override
    public void onStop() {
        super.onStop();
        coletasNumeroQuery.removeEventListener(coletasNumeroListener);
        coletasDataQuery.removeEventListener(coletasDataListener);
    }

}
