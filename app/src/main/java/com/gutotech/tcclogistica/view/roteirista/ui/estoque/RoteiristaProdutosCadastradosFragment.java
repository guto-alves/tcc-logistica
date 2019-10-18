package com.gutotech.tcclogistica.view.roteirista.ui.estoque;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.Produto;
import com.gutotech.tcclogistica.view.adapter.FuncionarioCadastradoAdapter;
import com.gutotech.tcclogistica.view.adapter.ProdutoEstoqueAdapter;

import java.util.ArrayList;
import java.util.List;

public class RoteiristaProdutosCadastradosFragment extends Fragment {

    public RoteiristaProdutosCadastradosFragment() {
    }

    private RecyclerView produtosRecyclerView;
    private List<Produto> listaProdutos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_produtos_cadastrados, container, false);

        produtosRecyclerView = root.findViewById(R.id.produtosRecyclerView);

        ProdutoEstoqueAdapter produtoEstoqueAdapter = new ProdutoEstoqueAdapter(listaProdutos);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        produtosRecyclerView.setLayoutManager(layoutManager);
        produtosRecyclerView.setHasFixedSize(true);
        produtosRecyclerView.setAdapter(produtoEstoqueAdapter);

        return root;
    }

}
