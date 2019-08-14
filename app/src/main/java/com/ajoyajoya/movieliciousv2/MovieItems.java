package com.ajoyajoya.movieliciousv2;

import org.json.JSONObject;

class MovieItems {

    private int movieId;
    private String moviePoster;
    private String movieName;
    private String movieRated;
    private String movieDesc;
    private String releaseDate;



    public MovieItems(JSONObject object) {
        try {
            int movieId = object.getInt("id");
            String moviePoster = object.getString("poster_path");
            String movieName = object.getString("original_title");
            String movieRated = object.getString("vote_average");
            String movieDesc = object.getString("overview");
            String releaseDate = object.getString("release_date");

            this.movieId = movieId;
            this.moviePoster = moviePoster;
            this.movieName = movieName;
            this.movieRated = movieRated;
            this.movieDesc = movieDesc;
            this.releaseDate = releaseDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public int getMovieId() {
        return movieId;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getMovieRated() {
        return movieRated;
    }

    public String getMovieDesc() {
        return movieDesc;
    }

    public String getReleaseDate() {
        return releaseDate;
    }


}
