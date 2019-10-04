package com.example.viewcatalogue;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.viewcatalogue.adapter.ModelMovieAdapter;
import com.example.viewcatalogue.helper.CatalogueViewModel;
import com.example.viewcatalogue.helper.ModelMovie;

import java.util.ArrayList;

public class CatalogueReleasedActivity extends AppCompatActivity {
    private ArrayList<ModelMovie> modelMovies = new ArrayList<>();
    private CatalogueViewModel catalogueViewModel;
    private ModelMovieAdapter modelMovieAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue_released);
        progressBar = findViewById(R.id.progress_bar);
        RecyclerView rvReleased = findViewById(R.id.rv_released);
        progressBar.setVisibility(View.VISIBLE);
        modelMovieAdapter = new ModelMovieAdapter(this);
        modelMovieAdapter.notifyDataSetChanged();
        catalogueViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(CatalogueViewModel.class);
        loadReleased();
        rvReleased.setLayoutManager(new LinearLayoutManager(this));
        rvReleased.setAdapter(modelMovieAdapter);
        modelMovieAdapter.setOnItemClickCallback(new ModelMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ModelMovie modelMovie) {
                Toast.makeText(getApplicationContext(), modelMovie.getMovie_name(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadReleased() {
        progressBar.setVisibility(View.VISIBLE);
        catalogueViewModel.setListReleased();
        catalogueViewModel.getListReleased().observe(this, getReleased);
    }

    private Observer<ArrayList<ModelMovie>> getReleased = new Observer<ArrayList<ModelMovie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ModelMovie> modelM) {
            if (modelM != null){
                modelMovies.clear();
                modelMovies.addAll(modelM);
                modelMovieAdapter.setListModelMovie(modelMovies);
                progressBar.setVisibility(View.GONE);
            }
        }
    };
}
