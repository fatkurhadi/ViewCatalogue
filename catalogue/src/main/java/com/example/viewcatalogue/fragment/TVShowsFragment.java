package com.example.viewcatalogue.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.viewcatalogue.R;
import com.example.viewcatalogue.adapter.MainVPAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowsFragment extends Fragment {


    public TVShowsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View page = inflater.inflate(R.layout.fragment_tv_shows, container, false);
        TabLayout tvShowTabNav = page.findViewById(R.id.tv_tab_nav);
        ViewPager tvShowPage = page.findViewById(R.id.tv_page);
        MainVPAdapter mainVPAdapter = new MainVPAdapter(getChildFragmentManager());
        mainVPAdapter.addPage(new ItemTVShowsFragment(), getString(R.string.nav_tv_show));
        mainVPAdapter.addPage(new FavItemTVShowsFragment(), getString(R.string.nav_favorite));
        tvShowPage.setAdapter(mainVPAdapter);
        tvShowTabNav.setupWithViewPager(tvShowPage);
        return page;
    }

}
