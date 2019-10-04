package com.example.viewcatalogue.fragment;


import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewcatalogue.BuildConfig;
import com.example.viewcatalogue.NotifyPrefActivity;
import com.example.viewcatalogue.R;
import com.example.viewcatalogue.adapter.ModelMovieAdapter;
import com.example.viewcatalogue.helper.CatalogueViewModel;
import com.example.viewcatalogue.helper.ModelMovie;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemMoviesFragment extends Fragment {
    private ArrayList<ModelMovie> modelMovies = new ArrayList<>();
    private ProgressBar progressBar;
    private ModelMovieAdapter modelMovieAdapter;
    private CatalogueViewModel catalogueViewModel;
    private TextView txtNoInternet;

    public ItemMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_item_movies, container, false);
        RecyclerView rvMovies = v.findViewById(R.id.rv_movies);
        progressBar = v.findViewById(R.id.progress_bar);
        txtNoInternet = v.findViewById(R.id.no_internet);
        progressBar.setVisibility(View.VISIBLE);
        txtNoInternet.setVisibility(View.GONE);
        setHasOptionsMenu(true);
        modelMovieAdapter = new ModelMovieAdapter(getActivity());
        modelMovieAdapter.notifyDataSetChanged();
        catalogueViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(CatalogueViewModel.class);
        loadMovie();
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setAdapter(modelMovieAdapter);
        modelMovieAdapter.setOnItemClickCallback(new ModelMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ModelMovie modelMovie) {
                Toast.makeText(getContext(), modelMovie.getMovie_name(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private void loadMovie(){
        progressBar.setVisibility(View.VISIBLE);
        txtNoInternet.setVisibility(View.GONE);
        catalogueViewModel.setListMovie();
        catalogueViewModel.getListMovie().observe(this, getModelMovie);
    }

    private Observer<ArrayList<ModelMovie>> getModelMovie = new Observer<ArrayList<ModelMovie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ModelMovie> modelM) {
            if (modelM != null){
                modelMovies.clear();
                modelMovies.addAll(modelM);
                modelMovieAdapter.setListModelMovie(modelMovies);
                progressBar.setVisibility(View.GONE);
                txtNoInternet.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                txtNoInternet.setVisibility(View.VISIBLE);
            }
        }
    };

    private Observer<ArrayList<ModelMovie>> getResultM = new Observer<ArrayList<ModelMovie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ModelMovie> modelRM) {
            if (modelRM != null){
                modelMovieAdapter.setListModelMovie(modelRM);
                progressBar.setVisibility(View.GONE);
                txtNoInternet.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                txtNoInternet.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu_nav, menu);
        searchingMovie(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchingMovie(Menu menu) {
        SearchManager movieSearchManager;
        if (getContext() != null){
            movieSearchManager = (SearchManager)getContext().getSystemService(Context.SEARCH_SERVICE);
            if (movieSearchManager != null){
                final SearchView movieSearchView = (SearchView)(menu.findItem(R.id.nav_search).getActionView());
                movieSearchView.setSearchableInfo(movieSearchManager.getSearchableInfo(Objects.requireNonNull(getActivity()).getComponentName()));
                movieSearchView.setIconifiedByDefault(false);
                movieSearchView.setFocusable(true);
                movieSearchView.setIconified(false);
                movieSearchView.requestFocusFromTouch();
                movieSearchView.setMaxWidth(Integer.MAX_VALUE);
                movieSearchView.setQueryHint(getString(R.string.movie_search));
                SearchView.OnQueryTextListener queryListen = new SearchView.OnQueryTextListener(){

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        movieSearchView.setQuery(query, false);
                        movieSearchView.setIconified(false);
                        movieSearchView.clearFocus();
                        String searchMUrl = BuildConfig.MOVIE_SEARCH_URL_BASE + BuildConfig.MY_API_KEY + "&language=en-US&query=" + query;
                        searchMQuery(searchMUrl);
                        keyboardM(movieSearchView);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (!newText.equals("")){
                            String searchMUrl = BuildConfig.MOVIE_SEARCH_URL_BASE + BuildConfig.MY_API_KEY + "&language=en-US&query=" + newText;
                            searchMQuery(searchMUrl);
                        }
                        return true;
                    }
                };
                movieSearchView.setOnQueryTextListener(queryListen);
                MenuItem searchMItem = menu.findItem(R.id.nav_search);
                searchMItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        loadMovie();
                        return true;
                    }
                });
            }
        }
    }

    private void searchMQuery(String searchMUrl) {
        modelMovies.clear();
        modelMovieAdapter.setListModelMovie(modelMovies);
        progressBar.setVisibility(View.VISIBLE);
        catalogueViewModel.setResultMovie(searchMUrl);
        catalogueViewModel.getResultMovie().observe(this, getResultM);
    }

    private void keyboardM(SearchView movieSearchView) {
        if (getContext() !=null){
            InputMethodManager inputMManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMManager.hideSoftInputFromWindow(movieSearchView.getWindowToken(),0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_notifier){
            Intent notifierIntent = new Intent(getActivity(), NotifyPrefActivity.class);
            startActivity(notifierIntent);
            return true;
        }
        if (item.getItemId() == R.id.nav_language){
            Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(languageIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
