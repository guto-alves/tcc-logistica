package com.gutotech.tcclogistica.view.roteirista.ui.nota;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.helper.RecyclerItemClickListener;
import com.gutotech.tcclogistica.model.Nota;
import com.gutotech.tcclogistica.view.adapter.NotasAdapter;

import java.util.ArrayList;
import java.util.List;

public class RoteiristaNotasCadastradasFragment extends Fragment {
    private RecyclerView notasRecyclerView;
    private NotasAdapter notasAdapter;
    private List<Nota> notasList = new ArrayList<>();

    private DatabaseReference notasReference;
    private Query notaQuery;
    private ValueEventListener notasListener;

    private TextView statusNotasTextView;

    public RoteiristaNotasCadastradasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_notas_cadastradas, container, false);

        notasRecyclerView = root.findViewById(R.id.notasRecyclerView);
        statusNotasTextView = root.findViewById(R.id.statusNotasTextView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        notasRecyclerView.setLayoutManager(layoutManager);
        notasRecyclerView.setHasFixedSize(true);
        notasRecyclerView.addOnItemTouchListener(notaItemTouchListener);

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

                if (notasList.size() == 0)
                    statusNotasTextView.setText("Nenhuma nota encontrada.");
                else
                    statusNotasTextView.setVisibility(View.GONE);

                notasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private final RecyclerView.OnItemTouchListener notaItemTouchListener = new RecyclerItemClickListener(getActivity(), notasRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Nota nota = notasList.get(position);

            NotaDialog notaDialog = new NotaDialog(getActivity(), nota);
            notaDialog.show();
        }

        @Override
        public void onLongItemClick(View view, int position) {
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    });


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
