package com.example.viewcatalogue.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewcatalogue.NotifyPrefActivity;
import com.example.viewcatalogue.R;
import com.example.viewcatalogue.adapter.ModelMovieAdapter;
import com.example.viewcatalogue.helper.ModelMovie;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.viewcatalogue.database.CatalogueDBContract.MOVIE_URI;
import static com.example.viewcatalogue.helper.CatalogueMapping.mapCursorM;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavItemMoviesFragment extends Fragment implements MovieCallback {
    private static final String EXTRA_STATE_Movie = "extra_state_movie";
    private RecyclerView rvFavMovies;
    private ModelMovieAdapter modelMovieAdapter;
    private TextView txtEmpty;


    public FavItemMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_fav_item_movies, container, false);
        rvFavMovies = v.findViewById(R.id.rv_fav_movies);
        txtEmpty = v.findViewById(R.id.no_collection);
        setHasOptionsMenu(true);
        rvFavMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        HandlerThread favMovieHandlerThread = new HandlerThread("DataObserver");
        favMovieHandlerThread.start();
        Handler favMovieHandler = new Handler(favMovieHandlerThread.getLooper());
        DataObserver favMovieObserver = new DataObserver(favMovieHandler, getContext());
        if (getActivity() != null){
            getActivity().getContentResolver().registerContentObserver(MOVIE_URI, true, favMovieObserver);
        }
        modelMovieAdapter = new ModelMovieAdapter(getActivity());
        rvFavMovies.setAdapter(modelMovieAdapter);
        modelMovieAdapter.setOnItemClickCallback(new ModelMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ModelMovie modelMovie) {
                Toast.makeText(getContext(), modelMovie.getMovie_name(), Toast.LENGTH_SHORT).show();
            }
        });
        if (savedInstanceState == null){
            new LoadMAsync(getContext(), this).execute();
        } else {
            ArrayList<ModelMovie> m = savedInstanceState.getParcelableArrayList(EXTRA_STATE_Movie);
            if (m != null){
                modelMovieAdapter.setListModelMovie(m);
            }
        }
        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE_Movie, modelMovieAdapter.getListModelMovie());
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMAsync(getContext(),this).execute();
    }

    @Override
    public void preExecute() {
        if (getActivity() != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtEmpty.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void postExecute(Cursor cursor) {
        txtEmpty.setVisibility(View.GONE);
        ArrayList<ModelMovie> modelMovies = mapCursorM(cursor);
        if (modelMovies.size() > 0){
            modelMovieAdapter.setListModelMovie(modelMovies);
        } else {
            rvFavMovies.setVisibility(View.INVISIBLE);
            txtEmpty.setVisibility(View.VISIBLE);
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }

    private static class LoadMAsync extends AsyncTask<Void, Void, Cursor>{
        private final WeakReference<Context> weakContext;
        private final WeakReference<MovieCallback> weakCallback;

        private LoadMAsync(Context context, MovieCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(MOVIE_URI, null, null, null, null);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pref_menu_nav, menu);
        super.onCreateOptionsMenu(menu, inflater);
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

interface MovieCallback {
    void preExecute();
    void postExecute(Cursor cursor);
}
