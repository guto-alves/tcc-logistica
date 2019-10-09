package com.gutotech.tcclogistica.view.adm.ui.coleta;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.tcclogistica.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdmColetasFragment extends Fragment {


    public AdmColetasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adm_coletas, container, false);
    }

}
