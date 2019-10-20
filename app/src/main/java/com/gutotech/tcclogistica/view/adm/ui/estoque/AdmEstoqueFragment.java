package com.gutotech.tcclogistica.view.adm.ui.estoque;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.view.adapter.ProdutoEstoqueAdapter;

public class AdmEstoqueFragment extends Fragment {

    private RecyclerView notasRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adm_estoque, container, false);

        notasRecyclerView = root.findViewById(R.id.notasRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        notasRecyclerView.setLayoutManager(layoutManager);
        notasRecyclerView.setHasFixedSize(true);

        return root;
    }

}