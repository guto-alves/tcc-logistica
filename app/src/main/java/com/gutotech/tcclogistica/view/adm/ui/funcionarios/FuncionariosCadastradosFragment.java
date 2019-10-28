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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class FuncionariosCadastradosFragment extends Fragment {
    private RecyclerView funcionariosRecyclerView;
    private FuncionariosAdapter funcionariosAdapter;
    private List<Funcionario> funcionariosList = new ArrayList<>();

    private DatabaseReference funcionariosReference;
    private Query funcionarioQuery;
    private ValueEventListener funcionariosListener;

    private String funcionarioProcurado = "";
    private String cargoSelected = "Todos";

    private TextView statusFuncionariosTextView;
    private TextView totalTextView;

    public FuncionariosCadastradosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adm_funcionarios_lista, container, false);

        statusFuncionariosTextView = root.findViewById(R.id.statusFuncionariosTextView);
        totalTextView = root.findViewById(R.id.totalTextView);

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
                funcionarioProcurado = newText;
                buscarFuncionario(funcionarioProcurado);
                return true;
            }
        });

        final Spinner cargosSpinner = root.findViewById(R.id.cargosSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.cargos_array_search, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cargosSpinner.setAdapter(adapter);
        cargosSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargoSelected = cargosSpinner.getSelectedItem().toString();
                buscarFuncionario(funcionarioProcurado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        funcionariosReference = ConfigFirebase.getDatabase().child("funcionario");

        return root;
    }

    private void buscarFuncionario(String query) {
        funcionarioQuery = funcionariosReference.orderByChild("nome").startAt(query).endAt(query + "\uf8ff");

        funcionariosListener = funcionarioQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                funcionariosList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Funcionario funcionario = data.getValue(Funcionario.class);
                    if (cargoSelected.equals("Todos"))
                        funcionariosList.add(funcionario);
                    else if (funcionario.getCargo().equals(cargoSelected))
                        funcionariosList.add(funcionario);
                }

                int totalFuncionarios = funcionariosList.size();
                totalTextView.setText(String.format(Locale.getDefault(), "Total: %d", totalFuncionarios));

                if (totalFuncionarios == 0) {
                    statusFuncionariosTextView.setText("Nenhum funcionário encontrado.");
                    statusFuncionariosTextView.setVisibility(View.VISIBLE);
                } else
                    statusFuncionariosTextView.setVisibility(View.GONE);

                funcionariosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
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

    @Override
    public void onStart() {
        super.onStart();
        buscarFuncionario(funcionarioProcurado);
    }

    @Override
    public void onStop() {
        super.onStop();
        funcionarioQuery.removeEventListener(funcionariosListener);
    }
}
