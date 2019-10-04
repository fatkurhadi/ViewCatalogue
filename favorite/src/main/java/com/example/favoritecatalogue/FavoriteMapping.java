package com.example.favoritecatalogue;

import android.database.Cursor;

import java.util.ArrayList;

import static com.example.favoritecatalogue.FavoriteDBContract.ItemField.ITEM_BACKDROP;
import static com.example.favoritecatalogue.FavoriteDBContract.ItemField.ITEM_DATE;
import static com.example.favoritecatalogue.FavoriteDBContract.ItemField.ITEM_ID;
import static com.example.favoritecatalogue.FavoriteDBContract.ItemField.ITEM_NAME;
import static com.example.favoritecatalogue.FavoriteDBContract.ItemField.ITEM_POPULARITY;
import static com.example.favoritecatalogue.FavoriteDBContract.ItemField.ITEM_POSTER;
import static com.example.favoritecatalogue.FavoriteDBContract.ItemField.ITEM_SCORE;
import static com.example.favoritecatalogue.FavoriteDBContract.ItemField.ITEM_SYNOPSIS;

public class FavoriteMapping {
    public static ArrayList<ModelMovie> mapCursorM(Cursor cursor){
        ArrayList<ModelMovie> modelMovies = new ArrayList<>();
        while (cursor.moveToNext()){
            String movie_id = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_ID));
            String movie_name = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_NAME));
            String movie_date = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_DATE));
            String movie_score = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_SCORE));
            String movie_popularity = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_POPULARITY));
            String movie_synopsis = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_SYNOPSIS));
            String movie_poster = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_POSTER));
            String movie_backdrop = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_BACKDROP));
            modelMovies.add(new ModelMovie(movie_id,movie_name,movie_date,movie_score,movie_popularity,movie_synopsis,movie_poster,movie_backdrop));
        }
        return modelMovies;
    }

    public static ArrayList<ModelTVShow> mapCursorTV(Cursor cursor){
        ArrayList<ModelTVShow> modelTVShows = new ArrayList<>();
        while (cursor.moveToNext()){
            String tv_show_id =cursor.getString(cursor.getColumnIndexOrThrow(ITEM_ID));
            String tv_show_name =cursor.getString(cursor.getColumnIndexOrThrow(ITEM_NAME));
            String tv_show_date =cursor.getString(cursor.getColumnIndexOrThrow(ITEM_DATE));
            String tv_show_score =cursor.getString(cursor.getColumnIndexOrThrow(ITEM_SCORE));
            String tv_show_popularity =cursor.getString(cursor.getColumnIndexOrThrow(ITEM_POPULARITY));
            String tv_show_synopsis =cursor.getString(cursor.getColumnIndexOrThrow(ITEM_SYNOPSIS));
            String tv_show_poster =cursor.getString(cursor.getColumnIndexOrThrow(ITEM_POSTER));
            String tv_show_backdrop =cursor.getString(cursor.getColumnIndexOrThrow(ITEM_BACKDROP));
            modelTVShows.add(new ModelTVShow(tv_show_id,tv_show_name,tv_show_date,tv_show_score,tv_show_popularity,tv_show_synopsis,tv_show_poster,tv_show_backdrop));
        }
        return modelTVShows;
    }
}
