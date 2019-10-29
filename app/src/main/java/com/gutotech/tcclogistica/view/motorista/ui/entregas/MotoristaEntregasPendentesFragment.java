package com.gutotech.tcclogistica.view.motorista.ui.entregas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.model.Entrega;
import com.gutotech.tcclogistica.model.FuncionarioOn;
import com.gutotech.tcclogistica.view.adapter.EntregasPendentesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MotoristaEntregasPendentesFragment extends Fragment {
    private List<Entrega> entregasList = new ArrayList<>();
    private EntregasPendentesAdapter entregasAdapter;

    private Query entregasPedentesQuery;
    private ValueEventListener entregasListener;

    public MotoristaEntregasPendentesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_motorista_entregas_pendentes, container, false);

        RecyclerView entregasRecyclerView = root.findViewById(R.id.entregasPendentesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        entregasRecyclerView.setLayoutManager(layoutManager);
        entregasRecyclerView.setHasFixedSize(true);
        entregasAdapter = new EntregasPendentesAdapter(getActivity(), entregasList);
        entregasRecyclerView.setAdapter(entregasAdapter);

        return root;
    }

    private void carregarEntregas() {
        DatabaseReference entregasReference = ConfigFirebase.getDatabase().child("entrega");

        entregasPedentesQuery = entregasReference.orderByChild("status").equalTo(Entrega.Status.PENDENTE.toString());

        entregasListener = entregasPedentesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                entregasList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Entrega entrega = data.getValue(Entrega.class);
                    if (entrega.getMotorista().getLogin().getUser().equals(FuncionarioOn.funcionario.getLogin().getUser()))
                        entregasList.add(entrega);
                }

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
        carregarEntregas();
    }

    @Override
    public void onStop() {
        super.onStop();
        entregasPedentesQuery.removeEventListener(entregasListener);
    }

}
