package com.example.viewcatalogue.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class CatalogueDBContract {
    public static final String AUTHORITY = "com.example.viewcatalogue";
    private static final String SCHEME = "content";
    public static final String MOVIE_TABLE = "table_movie";
    public static final String TV_SHOW_TABLE = "table_tv_show";

    public static final Uri MOVIE_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(MOVIE_TABLE)
            .build();

    public static final Uri TV_SHOW_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TV_SHOW_TABLE)
            .build();

    public CatalogueDBContract() {
    }

    public static final class ItemField implements BaseColumns {
        public static final String ITEM_ID = "item_id";
        public static final String ITEM_NAME = "item_name";
        public static final String ITEM_DATE = "item_date";
        public static final String ITEM_SCORE = "item_score";
        public static final String ITEM_POPULARITY = "item_popularity";
        public static final String ITEM_SYNOPSIS = "item_synopsis";
        public static final String ITEM_POSTER = "item_poster";
        public static final String ITEM_BACKDROP = "item_backdrop";
    }

    public static String getField(Cursor cursor, String fieldName){
        return cursor.getString(cursor.getColumnIndex(fieldName));
    }
}
