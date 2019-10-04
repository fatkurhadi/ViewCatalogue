package com.example.viewcatalogue.helper;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.viewcatalogue.database.CatalogueDBContract;

import static com.example.viewcatalogue.database.CatalogueDBContract.getField;

public class ModelMovie implements Parcelable {
    private String movie_id;
    private String movie_name;
    private String movie_date;
    private String movie_score;
    private String movie_popularity;
    private String movie_synopsis;
    private String movie_poster;
    private String movie_backdrop;

    public ModelMovie(String movie_id, String movie_name, String movie_date, String movie_score, String movie_popularity, String movie_synopsis, String movie_poster, String movie_backdrop) {
        this.movie_id = movie_id;
        this.movie_name = movie_name;
        this.movie_date = movie_date;
        this.movie_score = movie_score;
        this.movie_popularity = movie_popularity;
        this.movie_synopsis = movie_synopsis;
        this.movie_poster = movie_poster;
        this.movie_backdrop = movie_backdrop;
    }

    public ModelMovie(Cursor cursor) {
        this.movie_id = getField(cursor, CatalogueDBContract.ItemField.ITEM_ID);
        this.movie_name = getField(cursor, CatalogueDBContract.ItemField.ITEM_NAME);
        this.movie_date = getField(cursor, CatalogueDBContract.ItemField.ITEM_DATE);
        this.movie_score = getField(cursor, CatalogueDBContract.ItemField.ITEM_SCORE);
        this.movie_popularity = getField(cursor, CatalogueDBContract.ItemField.ITEM_POPULARITY);
        this.movie_synopsis = getField(cursor, CatalogueDBContract.ItemField.ITEM_SYNOPSIS);
        this.movie_poster = getField(cursor, CatalogueDBContract.ItemField.ITEM_POSTER);
        this.movie_backdrop = getField(cursor, CatalogueDBContract.ItemField.ITEM_BACKDROP);
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getMovie_date() {
        return movie_date;
    }

    public void setMovie_date(String movie_date) {
        this.movie_date = movie_date;
    }

    public String getMovie_score() {
        return movie_score;
    }

    public void setMovie_score(String movie_score) {
        this.movie_score = movie_score;
    }

    public String getMovie_popularity() {
        return movie_popularity;
    }

    public void setMovie_popularity(String movie_popularity) {
        this.movie_popularity = movie_popularity;
    }

    public String getMovie_synopsis() {
        return movie_synopsis;
    }

    public void setMovie_synopsis(String movie_synopsis) {
        this.movie_synopsis = movie_synopsis;
    }

    public String getMovie_poster() {
        return movie_poster;
    }

    public void setMovie_poster(String movie_poster) {
        this.movie_poster = movie_poster;
    }

    public String getMovie_backdrop() {
        return movie_backdrop;
    }

    public void setMovie_backdrop(String movie_backdrop) {
        this.movie_backdrop = movie_backdrop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.movie_id);
        dest.writeString(this.movie_name);
        dest.writeString(this.movie_date);
        dest.writeString(this.movie_score);
        dest.writeString(this.movie_popularity);
        dest.writeString(this.movie_synopsis);
        dest.writeString(this.movie_poster);
        dest.writeString(this.movie_backdrop);
    }

    public ModelMovie() {
    }

    public ModelMovie(Parcel in) {
        this.movie_id = in.readString();
        this.movie_name = in.readString();
        this.movie_date = in.readString();
        this.movie_score = in.readString();
        this.movie_popularity = in.readString();
        this.movie_synopsis = in.readString();
        this.movie_poster = in.readString();
        this.movie_backdrop = in.readString();
    }

    public static final Parcelable.Creator<ModelMovie> CREATOR = new Parcelable.Creator<ModelMovie>() {
        @Override
        public ModelMovie createFromParcel(Parcel source) {
            return new ModelMovie(source);
        }

        @Override
        public ModelMovie[] newArray(int size) {
            return new ModelMovie[size];
        }
    };
}
