package com.gutotech.tcclogistica.view.adm.ui.estoque;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.view.adapter.PageAdapter;

public class EstoqueFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_estoque, container, false);


        TabLayout tabLayout = root.findViewById(R.id.tabLayout);
        TabItem novoTabItem = root.findViewById(R.id.adicionarProdutoTabItem);
        TabItem cadastradosTabItem = root.findViewById(R.id.produtosCadastradosTabItem);
        ViewPager viewPager = root.findViewById(R.id.viewPager);

        PageAdapter pageAdapter = new PageAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
////                if (tab.getText().toString().equals("Novo"))
////                    changeToFragment(new ProdutoNovoFragment());
////                else
////                    changeToFragment(new ProdutosCadastradosFragment());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });
//
//        changeToFragment(new ProdutoNovoFragment());

        return root;
    }

    private void changeToFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        //transaction.replace(R.id.frameConteiner, fragment);
        transaction.commit();
    }
}