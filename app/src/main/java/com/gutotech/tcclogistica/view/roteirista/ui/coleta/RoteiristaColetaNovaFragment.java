package com.gutotech.tcclogistica.view.roteirista.ui.coleta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.tcclogistica.R;

public class RoteiristaColetaNovaFragment extends Fragment {


    public RoteiristaColetaNovaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_roteirista_coletas_nova, container, false);
    }

}
