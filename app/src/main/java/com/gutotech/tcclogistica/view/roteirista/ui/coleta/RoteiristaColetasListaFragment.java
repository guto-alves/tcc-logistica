package com.gutotech.tcclogistica.view.roteirista.ui.coleta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Coleta;
import com.gutotech.tcclogistica.view.adapter.ColetaRealizadaAdapter;
import com.gutotech.tcclogistica.view.adapter.FuncionarioCadastradoAdapter;

import java.util.ArrayList;
import java.util.List;

public class RoteiristaColetasListaFragment extends Fragment {


    public RoteiristaColetasListaFragment() {
    }

    private RecyclerView coletaRecyclerView;
    private List<Coleta> listaColetas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_coletas_lista, container, false);

        coletaRecyclerView = root.findViewById(R.id.coletaRecyclerView);

        ColetaRealizadaAdapter coletaRealizadaAdapter = new ColetaRealizadaAdapter(listaColetas);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        coletaRecyclerView.setLayoutManager(layoutManager);
        coletaRecyclerView.setHasFixedSize(true);
        coletaRecyclerView.setAdapter(coletaRealizadaAdapter);

        return root;
    }

}
