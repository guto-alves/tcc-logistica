package com.gutotech.tcclogistica.adm.ui.motoristas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.tcclogistica.R;

public class MotoristaNovoFragment extends Fragment {


    public MotoristaNovoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_motorista_novo, container, false);

        return root;
    }

}
