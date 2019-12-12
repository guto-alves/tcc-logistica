package com.gutotech.tcclogistica.view.roteirista.ui.veiculo;

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
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.Veiculo;
import com.gutotech.tcclogistica.view.adapter.EntregasAdapter;
import com.gutotech.tcclogistica.view.adapter.MotoristasVeiculosAdapter;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class VeiculosMotoristasListaFragment extends Fragment {
    private List<Funcionario> motoristasList = new LinkedList<>();
    private MotoristasVeiculosAdapter adapter;

    private DatabaseReference motoristasReference;
    private Query motoristasQuery;
    private ValueEventListener motoristasListener;

    private TextView statusTextView;
    private ProgressBar progressBar;

    public VeiculosMotoristasListaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_veiculos_motoristas_lista, container, false);

        statusTextView = root.findViewById(R.id.statusTextView);
        progressBar = root.findViewById(R.id.progressBar);

        RecyclerView motoristasVeiculosRecyclerView = root.findViewById(R.id.motoristasVeiculosRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        motoristasVeiculosRecyclerView.setLayoutManager(layoutManager);
        motoristasVeiculosRecyclerView.setHasFixedSize(true);
        adapter = new MotoristasVeiculosAdapter(getActivity(), motoristasList);
        motoristasVeiculosRecyclerView.setAdapter(adapter);
        swipe(motoristasVeiculosRecyclerView);

        motoristasReference = ConfigFirebase.getDatabase().child("funcionario");

        return root;
    }

    private void buscarMotoristas() {
        motoristasQuery = motoristasReference.orderByChild("cargo").equalTo(Funcionario.MOTORISTA);

        motoristasListener = motoristasQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                statusTextView.setText("Buscando alocações...");
                statusTextView.setVisibility(View.VISIBLE);

                motoristasList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Funcionario funcionario = data.getValue(Funcionario.class);
                    if (funcionario.getVeiculo().isAlocado())
                        motoristasList.add(funcionario);
                }

                if (motoristasList.size() == 0) {
                    statusTextView.setText("Nenhuma veículo está alocado.");
                    statusTextView.setVisibility(View.VISIBLE);
                } else
                    statusTextView.setVisibility(View.GONE);

                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void swipe(RecyclerView recyclerView) {
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
                excluirAlocacao(viewHolder);
            }
        };

        new ItemTouchHelper(callback).attachToRecyclerView(recyclerView);
    }

    private void excluirAlocacao(final RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Excluir Veículo");
        builder.setMessage("Você tem certeza que deseja excluir a alocação do veículo ao motorista? ");
        builder.setCancelable(false);

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = viewHolder.getAdapterPosition();
                Funcionario funcionario = motoristasList.get(position);

                funcionario.getVeiculo().setAlocado(false);
                funcionario.getVeiculo().salvar();
                funcionario.setVeiculo(new Veiculo());
                funcionario.salvar();
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.notifyDataSetChanged();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        buscarMotoristas();
    }

    @Override
    public void onStop() {
        super.onStop();
        motoristasQuery.removeEventListener(motoristasListener);
    }

}
