package com.example.viewcatalogue;

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
import com.example.viewcatalogue.helper.ModelMovie;
import com.example.viewcatalogue.helper.ModelTVShow;

import static com.example.viewcatalogue.database.CatalogueDBContract.MOVIE_URI;
import static com.example.viewcatalogue.database.CatalogueDBContract.TV_SHOW_URI;
import static com.example.viewcatalogue.helper.CatalogueContentValue.getCVM;
import static com.example.viewcatalogue.helper.CatalogueContentValue.getCVTv;

public class CatalogueDetailActivity extends AppCompatActivity {
    public static final String EXTRA_KEY_Movie = "extra_movie";
    public static final String EXTRA_KEY_TVShow = "extra_tv_show";
    private ModelMovie modelMovie;
    private ModelTVShow modelTVShow;
    private boolean checkDetail = false, checkFavorite = false;
    private ImageView imgPosterReceived, imgBackdropReceived, btnFav;
    private TextView nameReceived, dateReceived, scoreReceived, popularityReceived, synopsisReceived, txtFav;

    Uri detailUri;
    Cursor detailCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue_detail);

        imgPosterReceived = findViewById(R.id.img_poster_received);
        imgBackdropReceived = findViewById(R.id.img_backdrop_received);
        btnFav = findViewById(R.id.btn_fav);
        nameReceived = findViewById(R.id.name_received);
        dateReceived = findViewById(R.id.date_received);
        scoreReceived = findViewById(R.id.score_received);
        popularityReceived = findViewById(R.id.popularity_received);
        synopsisReceived = findViewById(R.id.synopsis_received);
        txtFav = findViewById(R.id.txt_fav);

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
            if (modelMovie != null){
                checkFavorite = true;
            } else {
                checkFavorite = false;
                modelMovie = getIntent().getParcelableExtra(EXTRA_KEY_Movie);
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
            if (modelTVShow != null){
                checkFavorite = true;
            } else {
                checkFavorite = false;
                modelTVShow = getIntent().getParcelableExtra(EXTRA_KEY_TVShow);
            }
            setDTV();
        }
        favChange();
        btnFav.setOnClickListener(favListen);
    }

    private void favChange() {
        if (checkFavorite){
            Glide.with(this)
                    .load("")
                    .placeholder(R.drawable.icon_favorite)
                    .into(btnFav);
            txtFav.setText(getString(R.string.fav_drop));
        } else {
            Glide.with(this)
                    .load("")
                    .placeholder(R.drawable.icon_favorite_border)
                    .into(btnFav);
            txtFav.setText(getString(R.string.fav_add));
        }
    }

    private View.OnClickListener favListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!checkFavorite){
                if (checkDetail){
                    checkFavorite = true;
                    ContentValues values = getCVM(modelMovie);
                    getContentResolver().insert(MOVIE_URI, values);
                    Toast.makeText(getApplicationContext(), modelMovie.getMovie_name() + " " + getString(R.string.added), Toast.LENGTH_SHORT).show();
                    favChange();
                } else {
                    checkFavorite = true;
                    ContentValues values = getCVTv(modelTVShow);
                    getContentResolver().insert(TV_SHOW_URI, values);
                    Toast.makeText(getApplicationContext(), modelTVShow.getTv_show_name() + " " + getString(R.string.added), Toast.LENGTH_SHORT).show();
                    favChange();
                }
            } else {
                if (checkDetail){
                    checkFavorite = false;
                    getContentResolver().delete(detailUri, null, null);
                    Toast.makeText(getApplicationContext(), modelMovie.getMovie_name() + " " + getString(R.string.deleted), Toast.LENGTH_SHORT).show();
                    favChange();
                } else {
                    checkFavorite = false;
                    getContentResolver().delete(detailUri, null, null);
                    Toast.makeText(getApplicationContext(), modelTVShow.getTv_show_name() + " " + getString(R.string.deleted), Toast.LENGTH_SHORT).show();
                    favChange();
                }
            }
        }
    };

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
