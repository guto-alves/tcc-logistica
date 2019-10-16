package com.gutotech.tcclogistica.view.roteirista.ui.estoque;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.tcclogistica.R;

public class RoteiristaNotaCadastradasFragment extends Fragment {

    public RoteiristaNotaCadastradasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_notas_cadastradas, container, false);

        return root;
    }

}
