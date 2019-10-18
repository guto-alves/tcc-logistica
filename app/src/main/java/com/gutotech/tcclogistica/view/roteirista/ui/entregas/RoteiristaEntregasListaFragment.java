package com.gutotech.tcclogistica.view.roteirista.ui.entregas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.tcclogistica.R;

public class RoteiristaEntregasListaFragment extends Fragment {

    public RoteiristaEntregasListaFragment() {
    }

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_entregas_lista, container, false);

        recyclerView = root.findViewById(R.id.entregasRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());


        return root;
    }

}
