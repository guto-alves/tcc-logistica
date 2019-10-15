package com.gutotech.tcclogistica.view.roteirista.ui.entregas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.gutotech.tcclogistica.R;

public class RoteiristaEntregasListaFragment extends Fragment {

    public RoteiristaEntregasListaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_entregas_lista, container, false);


        return root;
    }

}
