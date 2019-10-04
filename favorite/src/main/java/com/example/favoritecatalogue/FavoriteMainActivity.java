package com.example.favoritecatalogue;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FavoriteMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_main);
        TabLayout tabNav = findViewById(R.id.tab_nav);
        ViewPager favPage = findViewById(R.id.fav_pager);
        FavPageAdapter favPageAdapter = new FavPageAdapter(getSupportFragmentManager());
        favPageAdapter.addPaging(new FavoriteMovieFragment(), getString(R.string.nav_movie));
        favPageAdapter.addPaging(new FavoriteTVShowFragment(), getString(R.string.nav_tv_show));
        favPage.setAdapter(favPageAdapter);
        tabNav.setupWithViewPager(favPage);
    }
}
