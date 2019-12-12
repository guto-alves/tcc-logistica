package com.gutotech.tcclogistica.view.roteirista.ui.coleta;

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
import com.gutotech.tcclogistica.helper.RecyclerItemClickListener;
import com.gutotech.tcclogistica.model.Coleta;
import com.gutotech.tcclogistica.model.Nota;
import com.gutotech.tcclogistica.view.adapter.ColetasAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RoteiristaColetasListaFragment extends Fragment {
    private RecyclerView coletasRecyclerView;
    private ColetasAdapter coletasAdapter;
    private List<Coleta> coletasList = new ArrayList<>();

    private Query coletasQuery;
    private ValueEventListener coletasListener;

    private String numeroColetaPesquisado = "";
    private String statusColetaPesquisada = "Todas";

    private TextView totalEncontradoTextView;
    private ProgressBar progressBar;
    private TextView statusPesquisaTextView;

    public RoteiristaColetasListaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_coletas_lista, container, false);

        totalEncontradoTextView = root.findViewById(R.id.totalEncontradoTextView);
        progressBar = root.findViewById(R.id.progressBar);
        statusPesquisaTextView = root.findViewById(R.id.statusPesquisaTextView);

        coletasRecyclerView = root.findViewById(R.id.coletasRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        coletasRecyclerView.setLayoutManager(layoutManager);
        coletasRecyclerView.setHasFixedSize(true);
        coletasAdapter = new ColetasAdapter(getActivity(), coletasList);
        coletasRecyclerView.setAdapter(coletasAdapter);
        coletasRecyclerView.addOnItemTouchListener(coletaItemTouchListener);

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
                statusColetaPesquisada = statusSpinner.getSelectedItem().toString();
                buscarColetas(numeroColetaPesquisado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return root;
    }

    private final RecyclerView.OnItemTouchListener coletaItemTouchListener = new RecyclerItemClickListener(getActivity(), coletasRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Coleta coleta = coletasList.get(position);

            ColetaDialog coletaDialog = new ColetaDialog(getActivity(), coleta);
            coletaDialog.show();
        }

        @Override
        public void onLongItemClick(View view, int position) {
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    });

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

                    if (statusColetaPesquisada.equals("Todas"))
                        coletasList.add(coleta);
                    else if (coleta.getStatus().toString().equals(statusColetaPesquisada))
                        coletasList.add(coleta);
                }

                int totalColetas = coletasList.size();
                totalEncontradoTextView.setText(String.format(Locale.getDefault(), "Total: %d", totalColetas));

                if (totalColetas == 0) {
                    statusPesquisaTextView.setText("Nenhuma coleta encontrada.");
                    statusPesquisaTextView.setVisibility(View.VISIBLE);
                } else
                    statusPesquisaTextView.setVisibility(View.GONE);

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
