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
import com.example.viewcatalogue.adapter.ModelTVShowAdapter;
import com.example.viewcatalogue.helper.CatalogueViewModel;
import com.example.viewcatalogue.helper.ModelTVShow;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemTVShowsFragment extends Fragment {
    private ArrayList<ModelTVShow> modelTVShows = new ArrayList<>();
    private ProgressBar progressBar;
    private ModelTVShowAdapter modelTVShowAdapter;
    private CatalogueViewModel catalogueViewModel;
    private TextView txtNoInternet;


    public ItemTVShowsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_item_tv_shows, container, false);
        RecyclerView rvTVShows = v.findViewById(R.id.rv_tv_shows);
        progressBar = v.findViewById(R.id.progress_bar);
        txtNoInternet = v.findViewById(R.id.no_internet);
        progressBar.setVisibility(View.VISIBLE);
        txtNoInternet.setVisibility(View.GONE);
        setHasOptionsMenu(true);
        modelTVShowAdapter = new ModelTVShowAdapter(getActivity());
        modelTVShowAdapter.notifyDataSetChanged();
        catalogueViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(CatalogueViewModel.class);
        loadTVShow();
        rvTVShows.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTVShows.setAdapter(modelTVShowAdapter);
        modelTVShowAdapter.setOnItemClickCallback(new ModelTVShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ModelTVShow modelTVShow) {
                Toast.makeText(getContext(), modelTVShow.getTv_show_name(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private Observer<ArrayList<ModelTVShow>> getModelTVShow = new Observer<ArrayList<ModelTVShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ModelTVShow> modelTV) {
            if (modelTV != null){
                modelTVShows.clear();
                modelTVShows.addAll(modelTV);
                modelTVShowAdapter.setListModelTVShow(modelTVShows);
                progressBar.setVisibility(View.GONE);
                txtNoInternet.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                txtNoInternet.setVisibility(View.VISIBLE);
            }
        }
    };

    private Observer<ArrayList<ModelTVShow>> getResultTV = new Observer<ArrayList<ModelTVShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ModelTVShow> modelRTV) {
            if (modelRTV != null){
                modelTVShowAdapter.setListModelTVShow(modelRTV);
                progressBar.setVisibility(View.GONE);
                txtNoInternet.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                txtNoInternet.setVisibility(View.VISIBLE);
            }
        }
    };

    private void loadTVShow(){
        progressBar.setVisibility(View.VISIBLE);
        txtNoInternet.setVisibility(View.GONE);
        catalogueViewModel.setListTVShow();
        catalogueViewModel.getListTVShow().observe(this, getModelTVShow);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu_nav, menu);
        searchingTVShow(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchingTVShow(Menu menu) {
        SearchManager tvShowSearchManager;
        if (getContext() != null){
            tvShowSearchManager = (SearchManager)getContext().getSystemService(Context.SEARCH_SERVICE);
            if (tvShowSearchManager != null){
                final SearchView tvShowSearchView = (SearchView)(menu.findItem(R.id.nav_search).getActionView());
                tvShowSearchView.setSearchableInfo(tvShowSearchManager.getSearchableInfo(Objects.requireNonNull(getActivity()).getComponentName()));
                tvShowSearchView.setIconifiedByDefault(false);
                tvShowSearchView.setFocusable(true);
                tvShowSearchView.setIconified(false);
                tvShowSearchView.requestFocusFromTouch();
                tvShowSearchView.setMaxWidth(Integer.MAX_VALUE);
                tvShowSearchView.setQueryHint(getString(R.string.tv_show_search));
                SearchView.OnQueryTextListener queryListen = new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        tvShowSearchView.setQuery(query, false);
                        tvShowSearchView.setIconified(false);
                        tvShowSearchView.clearFocus();
                        String searchTVUrl = BuildConfig.TV_SHOW_SEARCH_URL_BASE + BuildConfig.MY_API_KEY + "&language=en-US&query=" + query;
                        searchTVQuery(searchTVUrl);
                        keyboardTV(tvShowSearchView);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (!newText.equals("")) {
                            String searchTVUrl = BuildConfig.TV_SHOW_SEARCH_URL_BASE + BuildConfig.MY_API_KEY + "&language=en-US&query=" + newText;
                            searchTVQuery(searchTVUrl);
                        }
                        return true;
                    }
                };
                tvShowSearchView.setOnQueryTextListener(queryListen);
                MenuItem searchTVItem = menu.findItem(R.id.nav_search);
                searchTVItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        loadTVShow();
                        return true;
                    }
                });
            }
        }
    }

    private void searchTVQuery(String searchTVUrl) {
        modelTVShows.clear();
        modelTVShowAdapter.setListModelTVShow(modelTVShows);
        progressBar.setVisibility(View.VISIBLE);
        catalogueViewModel.setResultTVShow(searchTVUrl);
        catalogueViewModel.getResultTVShow().observe(this, getResultTV);
    }

    private void keyboardTV(SearchView tvShowSearchView) {
        if (getContext() != null){
            InputMethodManager inputTVManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputTVManager.hideSoftInputFromWindow(tvShowSearchView.getWindowToken(),0);
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
