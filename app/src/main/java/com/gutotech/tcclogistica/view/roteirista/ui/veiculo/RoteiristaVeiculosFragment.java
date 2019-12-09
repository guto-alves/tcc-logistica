package com.gutotech.tcclogistica.view.roteirista.ui.veiculo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gutotech.tcclogistica.R;

public class RoteiristaVeiculosFragment extends Fragment {

    public RoteiristaVeiculosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_veiculos, container, false);

        BottomNavigationView bottomNavigationView = root.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_nova:
                        changeToFragment(new VeiculoMotoristaNovoFragment());
                        break;
                    case R.id.action_cadastradas:
                        changeToFragment(new VeiculosMotoristasListaFragment());
                        break;
                }

                return true;
            }
        });

        changeToFragment(new VeiculoMotoristaNovoFragment());

        return root;
    }

    private void changeToFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameConteiner, fragment);
        transaction.commit();
    }
}
