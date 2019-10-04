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
public class MoviesFragment extends Fragment {


    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View page = inflater.inflate(R.layout.fragment_movies, container, false);
        TabLayout movieTabNav = page.findViewById(R.id.m_tab_nav);
        ViewPager moviePage = page.findViewById(R.id.m_page);
        MainVPAdapter mainVPAdapter = new MainVPAdapter(getChildFragmentManager());
        mainVPAdapter.addPage(new ItemMoviesFragment(), getString(R.string.nav_movie));
        mainVPAdapter.addPage(new FavItemMoviesFragment(), getString(R.string.nav_favorite));
        moviePage.setAdapter(mainVPAdapter);
        movieTabNav.setupWithViewPager(moviePage);
        return page;
    }

}
