package com.example.favoritecatalogue;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.favoritecatalogue.FavoriteDBContract.TV_SHOW_URI;
import static com.example.favoritecatalogue.FavoriteMapping.mapCursorTV;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTVShowFragment extends Fragment implements TVShowCallback {
    private static final String EXTRA_STATE_TVShow = "extra_state_tv_show";
    private RecyclerView rvFavTVShows;
    private ModelTVShowAdapter modelTVShowAdapter;
    private TextView txtEmpty;


    public FavoriteTVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
        rvFavTVShows = v.findViewById(R.id.rv_fav_tv_show);
        txtEmpty = v.findViewById(R.id.no_collection);
        setHasOptionsMenu(true);
        rvFavTVShows.setLayoutManager(new LinearLayoutManager(getActivity()));
        HandlerThread favTVShowHandlerThread = new HandlerThread("DataObserver");
        favTVShowHandlerThread.start();
        Handler favTVShowHandler = new Handler(favTVShowHandlerThread.getLooper());
        DataObserver favTVShowObserver = new DataObserver(favTVShowHandler, getContext());
        if (getActivity() != null){
            getActivity().getContentResolver().registerContentObserver(TV_SHOW_URI, true, favTVShowObserver);
        }
        modelTVShowAdapter = new ModelTVShowAdapter(getActivity());
        rvFavTVShows.setAdapter(modelTVShowAdapter);
        modelTVShowAdapter.setOnItemClickCallback(new ModelTVShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ModelTVShow modelTVShow) {
                Toast.makeText(getContext(), modelTVShow.getTv_show_name(), Toast.LENGTH_SHORT).show();
            }
        });
        if (savedInstanceState == null){
            new LoadTVAsync(getContext(), this).execute();
        } else {
            ArrayList<ModelTVShow> tv = savedInstanceState.getParcelableArrayList(EXTRA_STATE_TVShow);
            if (tv != null){
                modelTVShowAdapter.setListModelTVShow(tv);
            }
        }
        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE_TVShow, modelTVShowAdapter.getListModelTVShow());
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadTVAsync(getContext(), this).execute();
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
        ArrayList<ModelTVShow> modelTVShows = mapCursorTV(cursor);
        if (modelTVShows.size() > 0){
            modelTVShowAdapter.setListModelTVShow(modelTVShows);
        } else {
            rvFavTVShows.setVisibility(View.INVISIBLE);
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
        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }

    private static class LoadTVAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<TVShowCallback> weakCallback;

        private LoadTVAsync(Context context, TVShowCallback callback) {
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
            return context.getContentResolver().query(TV_SHOW_URI, null, null, null, null);
        }
    }
}

interface TVShowCallback {
    void preExecute();
    void postExecute(Cursor cursor);
}
