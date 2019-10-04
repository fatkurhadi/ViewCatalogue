package com.example.favoritecatalogue;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class FavoriteDetailActivity extends AppCompatActivity {
    public static final String EXTRA_KEY_Movie = "extra_movie";
    public static final String EXTRA_KEY_TVShow = "extra_tv_show";
    private ModelMovie modelMovie;
    private ModelTVShow modelTVShow;
    private boolean checkDetail = false;
    private ImageView imgPosterReceived, imgBackdropReceived;
    private TextView nameReceived, dateReceived, scoreReceived, popularityReceived, synopsisReceived;
    Uri detailUri;
    Cursor detailCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_detail);
        imgPosterReceived = findViewById(R.id.img_poster_received);
        imgBackdropReceived = findViewById(R.id.img_backdrop_received);
        nameReceived = findViewById(R.id.name_received);
        dateReceived = findViewById(R.id.date_received);
        scoreReceived = findViewById(R.id.score_received);
        popularityReceived = findViewById(R.id.popularity_received);
        synopsisReceived = findViewById(R.id.synopsis_received);

        if (getIntent().getParcelableExtra(EXTRA_KEY_Movie) != null){
            checkDetail = true;
            detailUri = getIntent().getData();
            detailCursor = getContentResolver().query(detailUri, null, null, null, null);
            if (detailCursor != null){
                if (detailCursor.moveToFirst()){
                    modelMovie = new ModelMovie(detailCursor);
                    detailCursor.close();
                }
            }
            setDM();
        } else {
            checkDetail = false;
            detailUri = getIntent().getData();
            detailCursor = getContentResolver().query(detailUri, null, null, null, null);
            if (detailCursor != null){
                if (detailCursor.moveToFirst()){
                    modelTVShow = new ModelTVShow(detailCursor);
                    detailCursor.close();
                }
            }
            setDTV();
        }
    }

    private void setDM() {
        Glide.with(this)
                .load(modelMovie.getMovie_poster())
                .into(imgPosterReceived);
        Glide.with(this)
                .load(modelMovie.getMovie_backdrop())
                .into(imgBackdropReceived);
        nameReceived.setText(modelMovie.getMovie_name());
        dateReceived.setText(modelMovie.getMovie_date());
        scoreReceived.setText(modelMovie.getMovie_score());
        popularityReceived.setText(modelMovie.getMovie_popularity());
        synopsisReceived.setText(modelMovie.getMovie_synopsis());
    }

    private void setDTV() {
        Glide.with(this)
                .load(modelTVShow.getTv_show_poster())
                .into(imgPosterReceived);
        Glide.with(this)
                .load(modelTVShow.getTv_show_backdrop())
                .into(imgBackdropReceived);
        nameReceived.setText(modelTVShow.getTv_show_name());
        dateReceived.setText(modelTVShow.getTv_show_date());
        scoreReceived.setText(modelTVShow.getTv_show_score());
        popularityReceived.setText(modelTVShow.getTv_show_popularity());
        synopsisReceived.setText(modelTVShow.getTv_show_synopsis());
    }
}
