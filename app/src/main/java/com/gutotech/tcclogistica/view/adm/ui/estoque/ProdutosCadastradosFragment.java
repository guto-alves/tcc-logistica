package com.gutotech.tcclogistica.view.adm.ui.estoque;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.tcclogistica.R;

public class ProdutosCadastradosFragment extends Fragment {

    public ProdutosCadastradosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_produtos_cadastrados, container, false);
    }

}
