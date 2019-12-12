package com.gutotech.tcclogistica.view.adm.ui.coleta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.model.Coleta;
import com.gutotech.tcclogistica.view.adapter.ColetasAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdmColetasFragment extends Fragment {
    private List<Coleta> coletasList = new ArrayList<>();
    private ColetasAdapter coletasAdapter;

    private Query coletasQuery;
    private ValueEventListener coletasListener;

    private String numeroColetaPesquisado = "";

    private TextView totalTextView;
    private String statusColeta = "Todas";
    private TextView statusColetasTextView;
    private ProgressBar progressBar;

    public AdmColetasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adm_coletas, container, false);

        totalTextView = root.findViewById(R.id.totalTextView);
        statusColetasTextView = root.findViewById(R.id.statusPesquisaTextView);
        progressBar = root.findViewById(R.id.progressBar);

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
                numeroColetaPesquisado = newText;
                buscarColetas(numeroColetaPesquisado);
                return true;
            }
        });

        final Spinner statusSpinner = root.findViewById(R.id.statusSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusColeta = statusSpinner.getSelectedItem().toString();
                buscarColetas(numeroColetaPesquisado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return root;
    }

    private void buscarColetas(String query) {
        DatabaseReference coletasReference = ConfigFirebase.getDatabase().child("coleta");

        coletasQuery = coletasReference.orderByChild("numero").startAt(query).endAt(query + "\uf8ff");

        coletasListener = coletasQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);

                coletasList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Coleta coleta = data.getValue(Coleta.class);

                    if (statusColeta.equals("Todas"))
                        coletasList.add(coleta);
                    else if (coleta.getStatus().toString().equals(statusColeta))
                        coletasList.add(coleta);
                }

                int totalColetas = coletasList.size();
                totalTextView.setText(String.format(Locale.getDefault(), "Total: %d", totalColetas));

                if (totalColetas == 0) {
                    statusColetasTextView.setText("Nenhuma coleta encontrada.");
                    statusColetasTextView.setVisibility(View.VISIBLE);
                } else
                    statusColetasTextView.setVisibility(View.GONE);

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
        buscarColetas(numeroColetaPesquisado);
    }

    @Override
    public void onStop() {
        super.onStop();
        coletasQuery.removeEventListener(coletasListener);
    }

}
