package com.example.viewcatalogue.helper;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.viewcatalogue.BuildConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class CatalogueViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ModelMovie>> listReleased = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ModelMovie>> listMovie = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ModelTVShow>> listTVShow = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ModelMovie>> resultMovie = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ModelTVShow>> resultTVShow = new MutableLiveData<>();

    public MutableLiveData<ArrayList<ModelMovie>> getListReleased() {
        return listReleased;
    }

    public void setListReleased() {
        this.listReleased = listReleased;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
        String today = day.format(new Date());

        AsyncHttpClient releasedClient = new AsyncHttpClient();
        String releasedUrl = BuildConfig.MOVIE_URL_BASE + BuildConfig.MY_API_KEY + "&primary_release_date.gte=" + today + "&primary_release_date.lte=" + today;
        final ArrayList<ModelMovie> listReleasedToday = new ArrayList<>();

        releasedClient.get(releasedUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String releasedResult = new String(responseBody);
                    JSONObject releasedObject = new JSONObject(releasedResult);
                    JSONArray releasedList = releasedObject.getJSONArray("results");

                    for (int pos = 0; pos < releasedList.length(); pos++){
                        JSONObject released = releasedList.getJSONObject(pos);
                        ModelMovie r = new ModelMovie();
                        r.setMovie_id(released.getString("id"));
                        r.setMovie_name(released.getString("title"));
                        r.setMovie_date(released.getString("release_date"));
                        r.setMovie_score(released.getString("vote_average"));
                        r.setMovie_popularity(released.getString("popularity"));
                        r.setMovie_synopsis(released.getString("overview"));
                        r.setMovie_poster(BuildConfig.IMAGES_URL_BASE + released.getString("poster_path"));
                        r.setMovie_backdrop(BuildConfig.IMAGES_URL_BASE + released.getString("backdrop_path"));
                        listReleasedToday.add(r);
                    }
                    listReleased.postValue(listReleasedToday);
                } catch (JSONException er){
                    Log.d("ExceptionReleased", er.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailReleased", error.getMessage());
            }
        });
    }

    public MutableLiveData<ArrayList<ModelMovie>> getListMovie() {
        return listMovie;
    }

    public void setListMovie() {
        AsyncHttpClient movieClient = new AsyncHttpClient();
        String movieUrl = BuildConfig.MOVIE_URL_BASE + BuildConfig.MY_API_KEY + "&language=en-US";
        final ArrayList<ModelMovie> listItemsMovie = new ArrayList<>();

        movieClient.get(movieUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String movieResult = new String(responseBody);
                    JSONObject movieObject = new JSONObject(movieResult);
                    JSONArray movieList = movieObject.getJSONArray("results");

                    for (int pos = 0; pos < movieList.length(); pos++){
                        JSONObject movie = movieList.getJSONObject(pos);
                        ModelMovie m = new ModelMovie();
                        m.setMovie_id(movie.getString("id"));
                        m.setMovie_name(movie.getString("title"));
                        m.setMovie_date(movie.getString("release_date"));
                        m.setMovie_score(movie.getString("vote_average"));
                        m.setMovie_popularity(movie.getString("popularity"));
                        m.setMovie_synopsis(movie.getString("overview"));
                        m.setMovie_poster(BuildConfig.IMAGES_URL_BASE + movie.getString("poster_path"));
                        m.setMovie_backdrop(BuildConfig.IMAGES_URL_BASE + movie.getString("backdrop_path"));
                        listItemsMovie.add(m);
                    }
                    listMovie.postValue(listItemsMovie);
                } catch (JSONException er){
                    Log.d("ExceptionMovie", er.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailMovie", error.getMessage());
            }
        });
    }

    public MutableLiveData<ArrayList<ModelTVShow>> getListTVShow() {
        return listTVShow;
    }

    public void setListTVShow() {
        AsyncHttpClient tvShowClient = new AsyncHttpClient();
        String tvShowUrl = BuildConfig.TV_SHOW_URL_BASE + BuildConfig.MY_API_KEY + "&language=en-US";
        final ArrayList<ModelTVShow> listItemsTVShow = new ArrayList<>();

        tvShowClient.get(tvShowUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String tvShowResult = new String(responseBody);
                    JSONObject tvShowObject = new JSONObject(tvShowResult);
                    JSONArray tvShowList = tvShowObject.getJSONArray("results");

                    for (int pos = 0; pos < tvShowList.length(); pos++){
                        JSONObject tvShow = tvShowList.getJSONObject(pos);
                        ModelTVShow tv = new ModelTVShow();
                        tv.setTv_show_id(tvShow.getString("id"));
                        tv.setTv_show_name(tvShow.getString("name"));
                        tv.setTv_show_date(tvShow.getString("first_air_date"));
                        tv.setTv_show_score(tvShow.getString("vote_average"));
                        tv.setTv_show_popularity(tvShow.getString("popularity"));
                        tv.setTv_show_synopsis(tvShow.getString("overview"));
                        tv.setTv_show_poster(BuildConfig.IMAGES_URL_BASE + tvShow.getString("poster_path"));
                        tv.setTv_show_backdrop(BuildConfig.IMAGES_URL_BASE + tvShow.getString("backdrop_path"));
                        listItemsTVShow.add(tv);
                    }
                    listTVShow.postValue(listItemsTVShow);
                } catch (Exception er){
                    Log.d("ExceptionTVShow", er.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailTVShow", error.getMessage());
            }
        });
    }

    public MutableLiveData<ArrayList<ModelMovie>> getResultMovie() {
        return resultMovie;
    }

    public void setResultMovie(String url) {
        AsyncHttpClient resultMovieClient = new AsyncHttpClient();
        final ArrayList<ModelMovie> listItemsRM = new ArrayList<>();
        resultMovieClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resultM = new String(responseBody);
                    JSONObject resultMObject = new JSONObject(resultM);
                    JSONArray resultMList = resultMObject.getJSONArray("results");
                    for (int pos=0; pos<resultMList.length(); pos++){
                        JSONObject movieResult = resultMList.getJSONObject(pos);
                        ModelMovie rm = new ModelMovie();
                        String idMovie = String.valueOf(movieResult.getInt("id"));
                        rm.setMovie_id(idMovie);
                        rm.setMovie_name(movieResult.getString("title"));
                        if(movieResult.getString("release_date").equals("")){
                            rm.setMovie_date("-");
                        }else {
                            rm.setMovie_date(movieResult.getString("release_date"));
                        }
                        if(String.valueOf(movieResult.getDouble("vote_average")).equals("")){
                            rm.setMovie_score("-");
                        }else {
                            rm.setMovie_score(String.valueOf(movieResult.getDouble("vote_average")));
                        }
                        if(String.valueOf(movieResult.getDouble("popularity")).equals("")){
                            rm.setMovie_popularity("-");
                        }else {
                            rm.setMovie_popularity(String.valueOf(movieResult.getDouble("popularity")));
                        }
                        if(movieResult.getString("overview").equals("")){
                            rm.setMovie_synopsis("-");
                        }else {
                            rm.setMovie_synopsis(movieResult.getString("overview"));
                        }
                        if(movieResult.getString("poster_path").equals("")){
                            rm.setMovie_poster("-");
                        }else {
                            rm.setMovie_poster(BuildConfig.IMAGES_URL_BASE + movieResult.getString("poster_path"));
                        }
                        if(movieResult.getString("backdrop_path").equals("")){
                            rm.setMovie_backdrop("-");
                        }else {
                            rm.setMovie_backdrop(BuildConfig.IMAGES_URL_BASE + movieResult.getString("backdrop_path"));
                        }
                        listItemsRM.add(rm);
                    }
                    resultMovie.postValue(listItemsRM);
                } catch (JSONException er) {
                    Log.d("SearchModelMovie : ", er.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailSearchM : ", error.getMessage());
                resultMovie.postValue(listItemsRM);
            }
        });
    }

    public MutableLiveData<ArrayList<ModelTVShow>> getResultTVShow() {
        return resultTVShow;
    }

    public void setResultTVShow(String url) {
        AsyncHttpClient resultTVShowClient = new AsyncHttpClient();
        final ArrayList<ModelTVShow> listItemsRTV = new ArrayList<>();
        resultTVShowClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resultTV = new String(responseBody);
                    JSONObject resultTVObject = new JSONObject(resultTV);
                    JSONArray resultTVList = resultTVObject.getJSONArray("results");
                    for (int pos=0; pos<resultTVList.length(); pos++){
                        JSONObject tv_show_Result = resultTVList.getJSONObject(pos);
                        ModelTVShow rtv = new ModelTVShow();
                        String idTVShow = String.valueOf(tv_show_Result.getInt("id"));
                        rtv.setTv_show_id(idTVShow);
                        rtv.setTv_show_name(tv_show_Result.getString("name"));
                        if(tv_show_Result.getString("first_air_date").equals("")){
                            rtv.setTv_show_date("-");
                        }else {
                            rtv.setTv_show_date(tv_show_Result.getString("first_air_date"));
                        }
                        if(String.valueOf(tv_show_Result.getDouble("vote_average")).equals("")){
                            rtv.setTv_show_score("-");
                        }else {
                            rtv.setTv_show_score(String.valueOf(tv_show_Result.getDouble("vote_average")));
                        }
                        if(String.valueOf(tv_show_Result.getDouble("popularity")).equals("")){
                            rtv.setTv_show_popularity("-");
                        }else {
                            rtv.setTv_show_popularity(String.valueOf(tv_show_Result.getDouble("popularity")));
                        }
                        if(tv_show_Result.getString("overview").equals("")){
                            rtv.setTv_show_synopsis("-");
                        }else {
                            rtv.setTv_show_synopsis(tv_show_Result.getString("overview"));
                        }
                        if(tv_show_Result.getString("poster_path").equals("")){
                            rtv.setTv_show_poster("-");
                        }else {
                            rtv.setTv_show_poster(BuildConfig.IMAGES_URL_BASE + tv_show_Result.getString("poster_path"));
                        }
                        if(tv_show_Result.getString("backdrop_path").equals("")){
                            rtv.setTv_show_backdrop("-");
                        }else {
                            rtv.setTv_show_backdrop(BuildConfig.IMAGES_URL_BASE + tv_show_Result.getString("backdrop_path"));
                        }
                        listItemsRTV.add(rtv);
                    }
                    resultTVShow.postValue(listItemsRTV);
                } catch (JSONException er) {
                    Log.d("SearchModelTVShow : ", er.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailSearchTV : ", error.getMessage());
                resultTVShow.postValue(listItemsRTV);
            }
        });
    }
}
