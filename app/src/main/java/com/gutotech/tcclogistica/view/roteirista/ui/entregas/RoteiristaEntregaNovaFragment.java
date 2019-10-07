package com.gutotech.tcclogistica.view.roteirista.ui.entregas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.tcclogistica.R;

public class RoteiristaEntregaNovaFragment extends Fragment {

    public RoteiristaEntregaNovaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_entrega_nova, container, false);

        return root;
    }

}
