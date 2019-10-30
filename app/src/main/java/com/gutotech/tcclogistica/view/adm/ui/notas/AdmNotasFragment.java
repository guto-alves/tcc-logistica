package com.gutotech.tcclogistica.view.adm.ui.notas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
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
import com.gutotech.tcclogistica.model.Nota;
import com.gutotech.tcclogistica.view.adapter.NotasAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdmNotasFragment extends Fragment {
    private NotasAdapter notasAdapter;
    private List<Nota> notasList = new ArrayList<>();

    private DatabaseReference notasReference;
    private Query notaQuery;
    private ValueEventListener notasListener;

    private TextView statusNotasTextView;
    private TextView totalTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adm_notas, container, false);

        RecyclerView notasRecyclerView = root.findViewById(R.id.notasRecyclerView);
        statusNotasTextView = root.findViewById(R.id.statusNotasTextView);
        totalTextView = root.findViewById(R.id.totalTextView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        notasRecyclerView.setLayoutManager(layoutManager);
        notasRecyclerView.setHasFixedSize(true);

        notasAdapter = new NotasAdapter(getActivity(), notasList);
        notasRecyclerView.setAdapter(notasAdapter);

        SearchView searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarNotas(newText);
                return true;
            }
        });

        notasReference = ConfigFirebase.getDatabase().child("nota");

        return root;
    }

    private void buscarNotas(String query) {
        notaQuery = notasReference.orderByChild("numero").startAt(query).endAt(query + "\uf8ff");

        notasListener = notaQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notasList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren())
                    notasList.add(data.getValue(Nota.class));

                int totalNotas = notasList.size();
                totalTextView.setText(String.format(Locale.getDefault(), "Total: %d", totalNotas));

                if (totalNotas == 0) {
                    statusNotasTextView.setText("Nenhuma nota encontrada.");
                    statusNotasTextView.setVisibility(View.VISIBLE);
                } else
                    statusNotasTextView.setVisibility(View.GONE);

                notasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        buscarNotas("");
    }

    @Override
    public void onStop() {
        super.onStop();
        notaQuery.removeEventListener(notasListener);
    }

}