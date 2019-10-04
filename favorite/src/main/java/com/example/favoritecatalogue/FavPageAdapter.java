package com.example.favoritecatalogue;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class FavPageAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> page = new ArrayList<>();
    private final ArrayList<String> title = new ArrayList<>();

    FavPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return page.get(i);
    }

    @Override
    public int getCount() {
        return title.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

    void addPaging(Fragment p, String t){
        page.add(p);
        title.add(t);
    }
}
