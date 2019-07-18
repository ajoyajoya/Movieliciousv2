package com.ajoyajoya.movieliciousv2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.res.Resources;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;


public class MainViewModel extends ViewModel {

    private static final String API_KEY = "6c850abf79ae2a311643afba9e9ff654";
    private MutableLiveData<ArrayList<MovieItems>> listMovies = new MutableLiveData<>();

    private MutableLiveData<ArrayList<TvItems>> listTv = new MutableLiveData<>();

    String languageID = "";


    void setMovies(final String movies) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieItems> listItems = new ArrayList<>();

        if (Locale.getDefault().getLanguage() == "in"){
            languageID = "id-ID";
        } else {
            languageID = "en-US";
        }

        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY+"&language="+languageID;
        System.out.println(url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems = new MovieItems(movie);
                        listItems.add(movieItems);
                    }
                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    void setTvs(final String tvies) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvItems> listItems = new ArrayList<>();

        if (Locale.getDefault().getLanguage() == "in"){
            languageID = "id-ID";
        } else {
            languageID = "en-US";
        }

        String url = "https://api.themoviedb.org/3/tv/top_rated?api_key="+API_KEY+"&language="+languageID;
        System.out.println(url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvies = list.getJSONObject(i);
                        TvItems tvItems = new TvItems(tvies);
                        listItems.add(tvItems);
                    }
                    listTv.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }



    LiveData<ArrayList<MovieItems>> getMovies() {
        return listMovies;
    }
    LiveData<ArrayList<TvItems>> getTvs() {
        return listTv;
    }

}
