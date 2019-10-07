package com.gutotech.tcclogistica.view.roteirista.ui.entregas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gutotech.tcclogistica.R;

public class RoteiristaEntregasFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_entregas, container, false);

        BottomNavigationView bottomNavigationView = root.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_novo:
                        changeToFragment(new RoteiristaEntregaNovaFragment());
                        break;
                    case R.id.action_cadastrados:
                        changeToFragment(new RoteiristaEntregasListaFragment());
                        break;
                }

                return true;
            }
        });

        changeToFragment(new RoteiristaEntregaNovaFragment());

        return root;
    }

    private void changeToFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameConteiner, fragment);
        transaction.commit();
    }
}