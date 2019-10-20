package com.gutotech.tcclogistica.view.adm.ui.entregas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;

public class AdmEntregasFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adm_entregas, container, false);


        RecyclerView entregasRecyclerView = root.findViewById(R.id.entregasRecyclerView);

        return root;
    }
}