package com.gutotech.tcclogistica.view.adm.ui.motoristas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.tcclogistica.R;

public class MotoristasCadastradosFragment extends Fragment {

    public MotoristasCadastradosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_motoristas_lista, container, false);

        return root;
    }

}
