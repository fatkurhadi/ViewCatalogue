package com.example.viewcatalogue.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainVPAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> pageFragment = new ArrayList<>();
    private final ArrayList<String> titleFragment = new ArrayList<>();

    public MainVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return pageFragment.get(i);
    }

    @Override
    public int getCount() {
        return titleFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleFragment.get(position);
    }

    public void addPage(Fragment page, String title){
        pageFragment.add(page);
        titleFragment.add(title);
    }
}
