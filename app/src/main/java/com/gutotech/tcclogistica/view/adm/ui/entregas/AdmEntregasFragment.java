package com.gutotech.tcclogistica.view.adm.ui.entregas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.model.Entrega;
import com.gutotech.tcclogistica.view.adapter.EntregasAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdmEntregasFragment extends Fragment {
    private List<Entrega> entregasList = new ArrayList<>();

    private EntregasAdapter entregasAdapter;

    private DatabaseReference entregasReference;
    private ValueEventListener entregasListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adm_entregas, container, false);

        RecyclerView entregasRecyclerView = root.findViewById(R.id.entregasRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        entregasRecyclerView.setLayoutManager(layoutManager);
        entregasRecyclerView.setHasFixedSize(true);
        entregasAdapter = new EntregasAdapter(getActivity(), entregasList);
        entregasRecyclerView.setAdapter(entregasAdapter);

        entregasReference = ConfigFirebase.getDatabase().child("entrega");

        return root;
    }

    private void carregarEntregas(String query) {
        entregasListener = entregasReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                entregasList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren())
                    entregasList.add(data.getValue(Entrega.class));

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
        carregarEntregas("");
    }

    @Override
    public void onStop() {
        super.onStop();
        entregasReference.removeEventListener(entregasListener);
    }
}