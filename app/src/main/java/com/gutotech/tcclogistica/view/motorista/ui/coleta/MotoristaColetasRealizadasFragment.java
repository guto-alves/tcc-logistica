package com.gutotech.tcclogistica.view.motorista.ui.coleta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.tcclogistica.R;

public class MotoristaColetasRealizadasFragment extends Fragment {

    public MotoristaColetasRealizadasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_motorista_coletas_realizadas, container, false);

        return root;
    }

}
