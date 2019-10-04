package com.example.viewcatalogue;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.viewcatalogue.fragment.MoviesFragment;
import com.example.viewcatalogue.fragment.TVShowsFragment;

public class CatalogueMainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment mainFragment;
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_movie:
                    mainFragment = new MoviesFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.catalogue_frame, mainFragment, mainFragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.nav_tv_show:
                    mainFragment = new TVShowsFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.catalogue_frame, mainFragment, mainFragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue_main);

        BottomNavigationView mainBottomNavigation = findViewById(R.id.nav_bottom);
        mainBottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if (savedInstanceState == null){
            mainBottomNavigation.setSelectedItemId(R.id.nav_movie);
        }
    }
}
