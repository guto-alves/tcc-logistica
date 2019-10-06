package com.gutotech.tcclogistica.view.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gutotech.tcclogistica.view.adm.ui.estoque.ProdutoNovoFragment;
import com.gutotech.tcclogistica.view.adm.ui.estoque.ProdutosCadastradosFragment;

public class PageAdapter extends FragmentPagerAdapter {
    private int numberOfTabs;

    public PageAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ProdutoNovoFragment();
            case 1:
                return new ProdutosCadastradosFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
