package com.example.viewcatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.viewcatalogue.R;
import com.example.viewcatalogue.database.CatalogueDBContract;
import com.example.viewcatalogue.database.CatalogueDBHelper;
import com.example.viewcatalogue.helper.ModelMovie;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CatalogueStackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final ArrayList<ModelMovie> modelMovies = new ArrayList<>();
    private final Context mContext;
    private Cursor mCursor;

    CatalogueStackRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        loadWidget();
    }

    @Override
    public void onDataSetChanged() {
        loadWidget();
    }

    private void loadWidget() {
        modelMovies.clear();
        CatalogueDBHelper catalogueDBHelper = new CatalogueDBHelper(mContext);
        SQLiteDatabase database = catalogueDBHelper.getWritableDatabase();
        mCursor = database.query(CatalogueDBContract.MOVIE_TABLE, null, null, null, null, null, null);
        if (mCursor != null){
            if (mCursor.moveToFirst()){
                do {
                    ModelMovie modelM = new ModelMovie(mCursor);
                    modelMovies.add(modelM);
                } while (mCursor.moveToNext());
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mCursor != null){
            mCursor.close();
        }
        modelMovies.clear();
    }

    @Override
    public int getCount() {
        return modelMovies.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews widgetRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.item_catalogue_widget);
        String backdropUrl = modelMovies.get(position).getMovie_backdrop();
        String nameUrl = modelMovies.get(position).getMovie_name();
        CharSequence moviesName = modelMovies.get(position).getMovie_name();
        try {
            Bitmap bitmapImages = Glide.with(mContext)
                    .asBitmap()
                    .load(backdropUrl)
                    .apply(new RequestOptions().centerCrop())
                    .submit()
                    .get();
            widgetRemoteViews.setImageViewBitmap(R.id.image_widget, bitmapImages);
            widgetRemoteViews.setTextViewText(R.id.text_widget, moviesName);
        } catch (InterruptedException | ExecutionException er){
            er.printStackTrace();
        }
        Bundle widgetBundle = new Bundle();
        widgetBundle.putString(CatalogueWidget.WIDGET_ITEM, nameUrl);
        Intent widgetIntent = new Intent();
        widgetIntent.putExtras(widgetBundle);
        widgetRemoteViews.setOnClickFillInIntent(R.id.image_widget, widgetIntent);
        return widgetRemoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
