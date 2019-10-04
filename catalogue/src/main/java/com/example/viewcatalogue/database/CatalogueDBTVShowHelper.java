package com.example.viewcatalogue.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.viewcatalogue.database.CatalogueDBContract.ItemField.ITEM_ID;
import static com.example.viewcatalogue.database.CatalogueDBContract.TV_SHOW_TABLE;

public class CatalogueDBTVShowHelper {
    private static CatalogueDBHelper catalogueDBHelper;
    private static CatalogueDBTVShowHelper INSTANCE;
    private static SQLiteDatabase database;

    private CatalogueDBTVShowHelper(Context context) {
        catalogueDBHelper = new CatalogueDBHelper(context);
    }

    public static CatalogueDBTVShowHelper getINSTANCE(Context context) {
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new CatalogueDBTVShowHelper(context);
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
        return database.query(TV_SHOW_TABLE, null, ITEM_ID + " = ? ", new String[]{id}, null, null, null, null);
    }

    public Cursor select(){
        return database.query(TV_SHOW_TABLE, null, null, null, null, null, ITEM_ID + " ASC", null);
    }

    public long insert(ContentValues values){
        return database.insert(TV_SHOW_TABLE, null, values);
    }

    public int delete(String id){
        return database.delete(TV_SHOW_TABLE, ITEM_ID + " = ? ", new String[]{id});
    }
}
