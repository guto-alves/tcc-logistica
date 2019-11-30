package com.gutotech.tcclogistica.view.adm.ui.entrega;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.model.Entrega;
import com.gutotech.tcclogistica.view.adapter.EntregasAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdmEntregasFragment extends Fragment {
    private List<Entrega> entregasList = new ArrayList<>();
    private EntregasAdapter entregasAdapter;

    private Query entregasQuery;
    private ValueEventListener listener;

    private String motoristaPesquisado = "";
    private String statusPesquisado = "Todas";

    private TextView totalTextView;
    private TextView statusTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adm_entregas, container, false);

        totalTextView = root.findViewById(R.id.totalTextView);
        statusTextView = root.findViewById(R.id.statusPesquisaTextView);

        RecyclerView entregasRecyclerView = root.findViewById(R.id.entregasRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        entregasRecyclerView.setLayoutManager(layoutManager);
        entregasRecyclerView.setHasFixedSize(true);
        entregasAdapter = new EntregasAdapter(getActivity(), entregasList);
        entregasRecyclerView.setAdapter(entregasAdapter);

        SearchView searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                motoristaPesquisado = newText;
                buscarEntregas(motoristaPesquisado);
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
                statusPesquisado = statusSpinner.getSelectedItem().toString();
                buscarEntregas(motoristaPesquisado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return root;
    }

    private void buscarEntregas(String query) {
        DatabaseReference entregasReference = ConfigFirebase.getDatabase().child("entrega");

        entregasQuery = entregasReference.orderByChild("nomeMotorista").startAt(query).endAt(query + "\uf8ff");

        listener = entregasQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                entregasList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Entrega entrega = data.getValue(Entrega.class);

                    if (statusPesquisado.equals("Todas"))
                        entregasList.add(entrega);
                    else if (entrega.getStatus().toString().equals(statusPesquisado))
                        entregasList.add(entrega);
                }

                int totalColetas = entregasList.size();
                totalTextView.setText(String.format(Locale.getDefault(), "Total: %d", totalColetas));

                if (totalColetas == 0) {
                    statusTextView.setText("Nenhuma entrega encontrada.");
                    statusTextView.setVisibility(View.VISIBLE);
                } else
                    statusTextView.setVisibility(View.GONE);

                entregasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        buscarEntregas(motoristaPesquisado);
    }

    @Override
    public void onStop() {
        super.onStop();
        entregasQuery.removeEventListener(listener);
    }
}