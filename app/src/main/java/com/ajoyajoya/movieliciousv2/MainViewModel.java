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

    private MutableLiveData<ArrayList<GenreItems>> listGenres = new MutableLiveData<>();

    //private final String LANGUAGE_ID = Integer.toString(R.string.language_id);

    String languageID = "";


    //2131689522

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

    void setGenres(final String genres) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<GenreItems> genreItemsItems = new ArrayList<>();

        if (Locale.getDefault().getLanguage() == "in"){
            languageID = "id-ID";
        } else {
            languageID = "en-US";
        }

        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key="+API_KEY+"&language="+languageID;
        System.out.println(url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("genres");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject genre = list.getJSONObject(i);
                        GenreItems genreItems = new GenreItems(genre);
                        genreItemsItems.add(genreItems);
                    }
                    listGenres.postValue(genreItemsItems);

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
    LiveData<ArrayList<GenreItems>> getGenres() {
        return listGenres;
    }
}
