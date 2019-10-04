package com.example.favoritecatalogue;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.favoritecatalogue.FavoriteDBContract.getField;

public class ModelTVShow implements Parcelable {
    private String tv_show_id;
    private String tv_show_name;
    private String tv_show_date;
    private String tv_show_score;
    private String tv_show_popularity;
    private String tv_show_synopsis;
    private String tv_show_poster;
    private String tv_show_backdrop;

    public ModelTVShow(String tv_show_id, String tv_show_name, String tv_show_date, String tv_show_score, String tv_show_popularity, String tv_show_synopsis, String tv_show_poster, String tv_show_backdrop) {
        this.tv_show_id = tv_show_id;
        this.tv_show_name = tv_show_name;
        this.tv_show_date = tv_show_date;
        this.tv_show_score = tv_show_score;
        this.tv_show_popularity = tv_show_popularity;
        this.tv_show_synopsis = tv_show_synopsis;
        this.tv_show_poster = tv_show_poster;
        this.tv_show_backdrop = tv_show_backdrop;
    }

    public ModelTVShow(Cursor cursor){
        this.tv_show_id = getField(cursor, FavoriteDBContract.ItemField.ITEM_ID);
        this.tv_show_name = getField(cursor, FavoriteDBContract.ItemField.ITEM_NAME);
        this.tv_show_date = getField(cursor, FavoriteDBContract.ItemField.ITEM_DATE);
        this.tv_show_score = getField(cursor, FavoriteDBContract.ItemField.ITEM_SCORE);
        this.tv_show_popularity = getField(cursor, FavoriteDBContract.ItemField.ITEM_POPULARITY);
        this.tv_show_synopsis = getField(cursor, FavoriteDBContract.ItemField.ITEM_SYNOPSIS);
        this.tv_show_poster = getField(cursor, FavoriteDBContract.ItemField.ITEM_POSTER);
        this.tv_show_backdrop = getField(cursor, FavoriteDBContract.ItemField.ITEM_BACKDROP);
    }

    public String getTv_show_id() {
        return tv_show_id;
    }

    public void setTv_show_id(String tv_show_id) {
        this.tv_show_id = tv_show_id;
    }

    public String getTv_show_name() {
        return tv_show_name;
    }

    public void setTv_show_name(String tv_show_name) {
        this.tv_show_name = tv_show_name;
    }

    public String getTv_show_date() {
        return tv_show_date;
    }

    public void setTv_show_date(String tv_show_date) {
        this.tv_show_date = tv_show_date;
    }

    public String getTv_show_score() {
        return tv_show_score;
    }

    public void setTv_show_score(String tv_show_score) {
        this.tv_show_score = tv_show_score;
    }

    public String getTv_show_popularity() {
        return tv_show_popularity;
    }

    public void setTv_show_popularity(String tv_show_popularity) {
        this.tv_show_popularity = tv_show_popularity;
    }

    public String getTv_show_synopsis() {
        return tv_show_synopsis;
    }

    public void setTv_show_synopsis(String tv_show_synopsis) {
        this.tv_show_synopsis = tv_show_synopsis;
    }

    public String getTv_show_poster() {
        return tv_show_poster;
    }

    public void setTv_show_poster(String tv_show_poster) {
        this.tv_show_poster = tv_show_poster;
    }

    public String getTv_show_backdrop() {
        return tv_show_backdrop;
    }

    public void setTv_show_backdrop(String tv_show_backdrop) {
        this.tv_show_backdrop = tv_show_backdrop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tv_show_id);
        dest.writeString(this.tv_show_name);
        dest.writeString(this.tv_show_date);
        dest.writeString(this.tv_show_score);
        dest.writeString(this.tv_show_popularity);
        dest.writeString(this.tv_show_synopsis);
        dest.writeString(this.tv_show_poster);
        dest.writeString(this.tv_show_backdrop);
    }

    public ModelTVShow() {
    }

    public ModelTVShow(Parcel in) {
        this.tv_show_id = in.readString();
        this.tv_show_name = in.readString();
        this.tv_show_date = in.readString();
        this.tv_show_score = in.readString();
        this.tv_show_popularity = in.readString();
        this.tv_show_synopsis = in.readString();
        this.tv_show_poster = in.readString();
        this.tv_show_backdrop = in.readString();
    }

    public static final Creator<ModelTVShow> CREATOR = new Creator<ModelTVShow>() {
        @Override
        public ModelTVShow createFromParcel(Parcel source) {
            return new ModelTVShow(source);
        }

        @Override
        public ModelTVShow[] newArray(int size) {
            return new ModelTVShow[size];
        }
    };
}
