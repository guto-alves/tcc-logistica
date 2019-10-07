package com.gutotech.tcclogistica.view.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gutotech.tcclogistica.view.roteirista.ui.estoque.RoteiristaProdutoNovoFragment;
import com.gutotech.tcclogistica.view.roteirista.ui.estoque.RoteiristaProdutosCadastradosFragment;

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
                return new RoteiristaProdutoNovoFragment();
            case 1:
                return new RoteiristaProdutosCadastradosFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
