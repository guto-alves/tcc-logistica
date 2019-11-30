package com.gutotech.tcclogistica.view.adm.ui.veiculo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.model.Coleta;
import com.gutotech.tcclogistica.model.Veiculo;
import com.gutotech.tcclogistica.view.adapter.ColetasAdapter;
import com.gutotech.tcclogistica.view.adapter.VeiculosAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VeiculoListaFragment extends Fragment {
    private List<Veiculo> veiculos = new ArrayList<>();
    private VeiculosAdapter veiculosAdapter;

    private Query veiculosQuery;
    private ValueEventListener valueEventListener;

    private String placaPesquisada = "";

    private TextView totalTextView;
    private TextView statusPesquisaTextView;

    public VeiculoListaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_veiculo_lista, container, false);

        totalTextView = root.findViewById(R.id.totalEncontradoTextView);
        statusPesquisaTextView = root.findViewById(R.id.statusPesquisaTextView);

        RecyclerView recyclerView = root.findViewById(R.id.veiculosRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        veiculosAdapter = new VeiculosAdapter(getActivity(), veiculos);
        recyclerView.setAdapter(veiculosAdapter);

        SearchView searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                placaPesquisada = newText;
                buscarVeiculo(placaPesquisada);
                return true;
            }
        });

        return root;
    }

    private void buscarVeiculo(String query) {
        DatabaseReference veiculoReference = ConfigFirebase.getDatabase().child("veiculo");

        veiculosQuery = veiculoReference.orderByChild("placa").startAt(query).endAt(query + "\uf8ff");

        valueEventListener = veiculosQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                veiculos.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Veiculo veiculo = data.getValue(Veiculo.class);
                    veiculos.add(veiculo);
                }

                int totalColetas = veiculos.size();
                totalTextView.setText(String.format(Locale.getDefault(), "Total: %d", totalColetas));

                if (totalColetas == 0) {
                    statusPesquisaTextView.setText("Nenhum veículo encontrado.");
                    statusPesquisaTextView.setVisibility(View.VISIBLE);
                } else
                    statusPesquisaTextView.setVisibility(View.GONE);

                veiculosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        buscarVeiculo(placaPesquisada);
    }

    @Override
    public void onStop() {
        super.onStop();
        veiculosQuery.removeEventListener(valueEventListener);
    }

}
