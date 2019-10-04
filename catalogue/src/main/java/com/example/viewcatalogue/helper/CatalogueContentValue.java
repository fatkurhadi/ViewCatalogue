package com.example.viewcatalogue.helper;

import android.content.ContentValues;

import static com.example.viewcatalogue.database.CatalogueDBContract.ItemField.ITEM_BACKDROP;
import static com.example.viewcatalogue.database.CatalogueDBContract.ItemField.ITEM_DATE;
import static com.example.viewcatalogue.database.CatalogueDBContract.ItemField.ITEM_ID;
import static com.example.viewcatalogue.database.CatalogueDBContract.ItemField.ITEM_NAME;
import static com.example.viewcatalogue.database.CatalogueDBContract.ItemField.ITEM_POPULARITY;
import static com.example.viewcatalogue.database.CatalogueDBContract.ItemField.ITEM_POSTER;
import static com.example.viewcatalogue.database.CatalogueDBContract.ItemField.ITEM_SCORE;
import static com.example.viewcatalogue.database.CatalogueDBContract.ItemField.ITEM_SYNOPSIS;

public class CatalogueContentValue {

    public static ContentValues getCVM(ModelMovie modelMovie){
        ContentValues values = new ContentValues();
        values.put(ITEM_ID, modelMovie.getMovie_id());
        values.put(ITEM_NAME, modelMovie.getMovie_name());
        values.put(ITEM_DATE, modelMovie.getMovie_date());
        values.put(ITEM_SCORE, modelMovie.getMovie_score());
        values.put(ITEM_POPULARITY, modelMovie.getMovie_popularity());
        values.put(ITEM_SYNOPSIS, modelMovie.getMovie_synopsis());
        values.put(ITEM_POSTER, modelMovie.getMovie_poster());
        values.put(ITEM_BACKDROP, modelMovie.getMovie_backdrop());
        return values;
    }

    public static ContentValues getCVTv(ModelTVShow modelTVShow){
        ContentValues values = new ContentValues();
        values.put(ITEM_ID, modelTVShow.getTv_show_id());
        values.put(ITEM_NAME, modelTVShow.getTv_show_name());
        values.put(ITEM_DATE, modelTVShow.getTv_show_date());
        values.put(ITEM_SCORE, modelTVShow.getTv_show_score());
        values.put(ITEM_POPULARITY, modelTVShow.getTv_show_popularity());
        values.put(ITEM_SYNOPSIS, modelTVShow.getTv_show_synopsis());
        values.put(ITEM_POSTER, modelTVShow.getTv_show_poster());
        values.put(ITEM_BACKDROP, modelTVShow.getTv_show_backdrop());
        return values;
    }
}
