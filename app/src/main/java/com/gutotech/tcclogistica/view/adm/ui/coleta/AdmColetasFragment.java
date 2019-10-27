package com.gutotech.tcclogistica.view.adm.ui.coleta;


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
import com.gutotech.tcclogistica.view.adapter.ColetasAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdmColetasFragment extends Fragment {
    private List<Coleta> coletasList = new ArrayList<>();

    private ColetasAdapter coletasAdapter;

    private DatabaseReference coletasReference;
    private Query coletasQuery;
    private ValueEventListener coletasListener;

    private TextView statusColetasTextView;

    public AdmColetasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adm_coletas, container, false);

        statusColetasTextView = root.findViewById(R.id.statusColetasTextView);
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
                buscarColetas(newText);
                return true;
            }
        });

        coletasReference = ConfigFirebase.getDatabase().child("coleta");

        return root;
    }

    private void buscarColetas(String query) {
        coletasQuery = coletasReference.orderByChild("numero").startAt(query).endAt(query + "\uf8ff");

        coletasListener = coletasQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                coletasList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren())
                    coletasList.add(data.getValue(Coleta.class));

                if (coletasList.size() == 0)
                    statusColetasTextView.setText("Nenhuma coleta encontrada.");
                else
                    statusColetasTextView.setVisibility(View.GONE);

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
        buscarColetas("");
    }

    @Override
    public void onStop() {
        super.onStop();
        coletasQuery.removeEventListener(coletasListener);
    }


}
