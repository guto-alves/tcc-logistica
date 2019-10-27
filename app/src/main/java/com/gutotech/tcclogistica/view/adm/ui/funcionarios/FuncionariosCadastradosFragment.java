package com.gutotech.tcclogistica.view.adm.ui.funcionarios;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.helper.RecyclerItemClickListener;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.FuncionarioOn;
import com.gutotech.tcclogistica.view.adapter.FuncionariosAdapter;
import com.gutotech.tcclogistica.view.adapter.LoginFuncionarioExpandableAdapter;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class FuncionariosCadastradosFragment extends Fragment {
    private RecyclerView funcionariosRecyclerView;
    private FuncionariosAdapter funcionariosAdapter;
    private List<Funcionario> funcionariosList = new ArrayList<>();

    private DatabaseReference funcionariosReference;
    private Query funcionarioQuery;
    private ValueEventListener funcionariosListener;

    private TextView statusFuncionariosTextView;

    public FuncionariosCadastradosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adm_funcionarios_lista, container, false);

        statusFuncionariosTextView = root.findViewById(R.id.statusFuncionariosTextView);
        statusFuncionariosTextView.setText("Carregando Funcionários ..");

        funcionariosRecyclerView = root.findViewById(R.id.funcionariosRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        funcionariosRecyclerView.setLayoutManager(layoutManager);
        funcionariosRecyclerView.setHasFixedSize(true);

        funcionariosAdapter = new FuncionariosAdapter(getActivity(), funcionariosList);
        funcionariosRecyclerView.setAdapter(funcionariosAdapter);
        funcionariosRecyclerView.addOnItemTouchListener(funcionarioItemTouchListener);

        SearchView searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarFuncionario(newText);
                return true;
            }
        });

        funcionariosReference = ConfigFirebase.getDatabase().child("funcionario");

        return root;
    }

    private final RecyclerView.OnItemTouchListener funcionarioItemTouchListener = new RecyclerItemClickListener(getActivity(), funcionariosRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Funcionario funcionario = funcionariosList.get(position);

            FuncionarioDialog funcionarioDialog = new FuncionarioDialog(getActivity(), funcionario);
            funcionarioDialog.show();
        }

        @Override
        public void onLongItemClick(View view, int position) {
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    });


    private void buscarFuncionario(String query) {
        funcionarioQuery = funcionariosReference.orderByChild("nome").startAt(query).endAt(query + "\uf8ff");

        funcionariosListener = funcionarioQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                funcionariosList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren())
                    funcionariosList.add(data.getValue(Funcionario.class));

                if (funcionariosList.size() == 0)
                    statusFuncionariosTextView.setText("Nenhum funcionário encontrado.");
                else
                    statusFuncionariosTextView.setVisibility(View.GONE);

                funcionariosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        buscarFuncionario("");
    }

    @Override
    public void onStop() {
        super.onStop();
        funcionarioQuery.removeEventListener(funcionariosListener);
    }
}
