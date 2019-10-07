package com.gutotech.tcclogistica.view.motorista.ui.entregas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gutotech.tcclogistica.R;

public class MotoristaEntregasFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_motorista_entregas, container, false);


        return root;
    }
}