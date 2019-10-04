package com.example.viewcatalogue.helper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.viewcatalogue.database.CatalogueDBMovieHelper;
import com.example.viewcatalogue.database.CatalogueDBTVShowHelper;
import com.example.viewcatalogue.fragment.FavItemMoviesFragment;
import com.example.viewcatalogue.fragment.FavItemTVShowsFragment;

import static com.example.viewcatalogue.database.CatalogueDBContract.AUTHORITY;
import static com.example.viewcatalogue.database.CatalogueDBContract.MOVIE_TABLE;
import static com.example.viewcatalogue.database.CatalogueDBContract.MOVIE_URI;
import static com.example.viewcatalogue.database.CatalogueDBContract.TV_SHOW_TABLE;
import static com.example.viewcatalogue.database.CatalogueDBContract.TV_SHOW_URI;

public class CatalogueProvider extends ContentProvider {
    private static final int MOVIE = 109;
    private static final int ID_MOVIE = 208;
    private static final int TV_SHOW = 307;
    private static final int ID_TV_SHOW = 406;
    private static final UriMatcher uriM = new UriMatcher(UriMatcher.NO_MATCH);
    private CatalogueDBMovieHelper catalogueDBMovieHelper;
    private CatalogueDBTVShowHelper catalogueDBTVShowHelper;

    static {
        uriM.addURI(AUTHORITY, MOVIE_TABLE, MOVIE);
        uriM.addURI(AUTHORITY, MOVIE_TABLE + "/#", ID_MOVIE);
        uriM.addURI(AUTHORITY, TV_SHOW_TABLE, TV_SHOW);
        uriM.addURI(AUTHORITY, TV_SHOW_TABLE + "/#", ID_TV_SHOW);
    }

    public CatalogueProvider() {
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int drop;
        catalogueDBMovieHelper.open();
        catalogueDBTVShowHelper.open();
        switch (uriM.match(uri)){
            case ID_MOVIE:
                drop = catalogueDBMovieHelper.delete(uri.getLastPathSegment());
                if (getContext() != null){
                    getContext().getContentResolver().notifyChange(MOVIE_URI, new FavItemMoviesFragment.DataObserver(new Handler(), getContext()));
                }
                break;
            case ID_TV_SHOW:
                drop = catalogueDBTVShowHelper.delete(uri.getLastPathSegment());
                if (getContext() != null){
                    getContext().getContentResolver().notifyChange(TV_SHOW_URI, new FavItemTVShowsFragment.DataObserver(new Handler(), getContext()));
                }
                break;
                default:
                    drop = 0;
                    break;
        }
        return drop;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        Uri uriP;
        catalogueDBMovieHelper.open();
        catalogueDBTVShowHelper.open();
        long add;
        switch (uriM.match(uri)){
            case MOVIE:
                add = catalogueDBMovieHelper.insert(values);
                uriP = Uri.parse(MOVIE_URI + "/" + add);
                if (getContext() != null){
                    getContext().getContentResolver().notifyChange(MOVIE_URI, new FavItemMoviesFragment.DataObserver(new Handler(), getContext()));
                }
                break;
            case TV_SHOW:
                add = catalogueDBTVShowHelper.insert(values);
                uriP = Uri.parse(TV_SHOW_URI + "/" + add);
                if (getContext() != null){
                    getContext().getContentResolver().notifyChange(TV_SHOW_URI, new FavItemTVShowsFragment.DataObserver(new Handler(), getContext()));
                }
                break;
                default:
                    throw new SQLException("FailedAdded " + uri);
        }
        return uriP;
    }

    @Override
    public boolean onCreate() {
        catalogueDBMovieHelper = CatalogueDBMovieHelper.getINSTANCE(getContext());
        catalogueDBTVShowHelper = CatalogueDBTVShowHelper.getINSTANCE(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        catalogueDBMovieHelper.open();
        catalogueDBTVShowHelper.open();
        Cursor cursor;
        switch (uriM.match(uri)){
            case MOVIE:
                cursor = catalogueDBMovieHelper.select();
                break;
            case ID_MOVIE:
                cursor = catalogueDBMovieHelper.selectById(uri.getLastPathSegment());
                break;
            case TV_SHOW:
                cursor = catalogueDBTVShowHelper.select();
                break;
            case ID_TV_SHOW:
                cursor = catalogueDBTVShowHelper.selectById(uri.getLastPathSegment());
                break;
                default:
                    cursor = null;
                    break;
        }
        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
