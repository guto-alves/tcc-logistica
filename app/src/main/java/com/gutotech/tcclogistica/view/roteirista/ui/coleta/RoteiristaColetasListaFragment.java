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

import java.util.ArrayList;
import java.util.List;

public class RoteiristaColetasListaFragment extends Fragment {
    private RecyclerView coletasRecyclerView;
    private List<Coleta> listaColetas = new ArrayList<>();

    public RoteiristaColetasListaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_coletas_lista, container, false);

        coletasRecyclerView = root.findViewById(R.id.coletasRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        coletasRecyclerView.setLayoutManager(layoutManager);
        coletasRecyclerView.setHasFixedSize(true);

        //ColetaRealizadaAdapter coletasAdapter = new ColetaRealizadaAdapter(listaColetas);
        //coletasRecyclerView.setAdapter(coletasAdapter);

        return root;
    }


}
