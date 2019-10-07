package com.gutotech.tcclogistica.view.adm.ui.funcionarios;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.tcclogistica.R;

public class FuncionarioNovoFragment extends Fragment {

    public FuncionarioNovoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adm_funcionario_novo, container, false);

        return root;
    }

}
