package com.example.viewcatalogue.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class CatalogueDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_fav";
    private static final int DB_VERSION = 1;

    private static final String CREATE_TB_MOVIE = "create table " + CatalogueDBContract.MOVIE_TABLE + " ( " +
            CatalogueDBContract.ItemField.ITEM_ID + " number primary key, " +
            CatalogueDBContract.ItemField.ITEM_NAME + " text, " +
            CatalogueDBContract.ItemField.ITEM_DATE + " text, " +
            CatalogueDBContract.ItemField.ITEM_SCORE + " text, " +
            CatalogueDBContract.ItemField.ITEM_POPULARITY + " text, " +
            CatalogueDBContract.ItemField.ITEM_SYNOPSIS + " text, " +
            CatalogueDBContract.ItemField.ITEM_POSTER + " text, " +
            CatalogueDBContract.ItemField.ITEM_BACKDROP + " text)";

    private static final String CREATE_TB_TV_SHOW = "create table " + CatalogueDBContract.TV_SHOW_TABLE + " ( " +
            CatalogueDBContract.ItemField.ITEM_ID + " number primary key, " +
            CatalogueDBContract.ItemField.ITEM_NAME + " text, " +
            CatalogueDBContract.ItemField.ITEM_DATE + " text, " +
            CatalogueDBContract.ItemField.ITEM_SCORE + " text, " +
            CatalogueDBContract.ItemField.ITEM_POPULARITY + " text, " +
            CatalogueDBContract.ItemField.ITEM_SYNOPSIS + " text, " +
            CatalogueDBContract.ItemField.ITEM_POSTER + " text, " +
            CatalogueDBContract.ItemField.ITEM_BACKDROP + " text)";

    public CatalogueDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TB_MOVIE);
        db.execSQL(CREATE_TB_TV_SHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + CatalogueDBContract.MOVIE_TABLE);
        db.execSQL("drop table if exists " + CatalogueDBContract.TV_SHOW_TABLE);
        onCreate(db);
    }
}
