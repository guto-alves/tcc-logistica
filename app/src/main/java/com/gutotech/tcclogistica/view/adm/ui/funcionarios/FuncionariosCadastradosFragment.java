package com.gutotech.tcclogistica.view.adm.ui.funcionarios;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.view.adapter.FuncionarioCadastradoAdapter;

import java.util.ArrayList;
import java.util.List;

public class FuncionariosCadastradosFragment extends Fragment {

    public FuncionariosCadastradosFragment() {
    }

    private RecyclerView funcionariosRecyclerView;
    private List<Funcionario> listaFuncionarios = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adm_funcionarios_lista, container, false);

        funcionariosRecyclerView = root.findViewById(R.id.funcionariosRecyclerView);

        FuncionarioCadastradoAdapter funcionarioCadastradoAdapter = new FuncionarioCadastradoAdapter(listaFuncionarios);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        funcionariosRecyclerView.setLayoutManager(layoutManager);
        funcionariosRecyclerView.setHasFixedSize(true);
        funcionariosRecyclerView.setAdapter(funcionarioCadastradoAdapter);

        return root;
    }

}
