package com.ajoyajoya.movieliciousv2;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieItems {

    private int movieId;
    private String moviePoster;
    private String movieName;
    private String movieRated;
    private String movieCategory;
    private String movieDesc;
    private String releaseDate;



    public MovieItems(JSONObject object) {
        try {
            int movieId = object.getInt("id");
            String moviePoster = object.getString("poster_path");
            String movieName = object.getString("original_title");
            String movieRated = object.getString("vote_average");
            String movieCategory = object.getString("genre_ids");
            String movieDesc = object.getString("overview");
            String releaseDate = object.getString("release_date");

            this.movieId = movieId;
            this.moviePoster = moviePoster;
            this.movieName = movieName;
            this.movieRated = movieRated;
            this.movieCategory = movieCategory;
            this.movieDesc = movieDesc;
            this.releaseDate = releaseDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieRated() {
        return movieRated;
    }

    public void setMovieRated(String movieRated) {
        this.movieRated = movieRated;
    }

    public String getMovieCategory() {
        return movieCategory;
    }

    public void setMovieCategory(String movieCategory) {
        this.movieCategory = movieCategory;
    }

    public String getMovieDesc() {
        return movieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }



}
