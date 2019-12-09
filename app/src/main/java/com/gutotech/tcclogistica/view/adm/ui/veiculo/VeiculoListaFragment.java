package com.gutotech.tcclogistica.view.adm.ui.veiculo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.gutotech.tcclogistica.model.Veiculo;
import com.gutotech.tcclogistica.view.adapter.VeiculosAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VeiculoListaFragment extends Fragment {
    private RecyclerView veiculosRecyclerView;
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

        veiculosRecyclerView = root.findViewById(R.id.veiculosRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        veiculosRecyclerView.setLayoutManager(layoutManager);
        veiculosRecyclerView.setHasFixedSize(true);
        veiculosAdapter = new VeiculosAdapter(getActivity(), veiculos);
        veiculosRecyclerView.setAdapter(veiculosAdapter);
        veiculosRecyclerView.addOnItemTouchListener(veiculoItemTouchListener);
        swipe();

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

    public void swipe() {
        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                excluirVeiculo(viewHolder);
            }
        };

        new ItemTouchHelper(callback).attachToRecyclerView(veiculosRecyclerView);
    }

    private void excluirVeiculo(final RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Excluir Veículo");
        builder.setMessage("Você tem certeza que deseja excluir o veículo? ");
        builder.setCancelable(false);

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = viewHolder.getAdapterPosition();
                Veiculo veiculo = veiculos.get(position);

                veiculo.excluir();
                veiculosAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                veiculosAdapter.notifyDataSetChanged();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private final RecyclerView.OnItemTouchListener veiculoItemTouchListener = new RecyclerItemClickListener(getActivity(), veiculosRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Veiculo funcionario = veiculos.get(position);

            VeiculoDialog funcionarioDialog = new VeiculoDialog(getActivity(), funcionario);
            funcionarioDialog.show();
        }

        @Override
        public void onLongItemClick(View view, int position) {
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    });

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
