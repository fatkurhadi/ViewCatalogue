package com.example.viewcatalogue.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.viewcatalogue.database.CatalogueDBContract.ItemField.ITEM_ID;
import static com.example.viewcatalogue.database.CatalogueDBContract.MOVIE_TABLE;

public class CatalogueDBMovieHelper {
    private static CatalogueDBHelper catalogueDBHelper;
    private static CatalogueDBMovieHelper INSTANCE;
    private static SQLiteDatabase database;

    private CatalogueDBMovieHelper(Context context){
        catalogueDBHelper = new CatalogueDBHelper(context);
    }

    public static CatalogueDBMovieHelper getINSTANCE(Context context) {
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new CatalogueDBMovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = catalogueDBHelper.getWritableDatabase();
    }

    public void close() {
        catalogueDBHelper.close();
        if (database.isOpen()){
            database.close();
        }
    }

    public Cursor selectById(String id){
        return database.query(MOVIE_TABLE, null, ITEM_ID + " = ? ", new String[]{id}, null, null, null, null);
    }

    public Cursor select(){
        return database.query(MOVIE_TABLE, null, null,null, null, null, ITEM_ID + " ASC", null);
    }

    public long insert(ContentValues values){
        return database.insert(MOVIE_TABLE, null, values);
    }

    public int delete(String id){
        return database.delete(MOVIE_TABLE, ITEM_ID + " = ? ", new String[]{id});
    }
}
