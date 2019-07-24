package com.ajoyajoya.movieliciousv2;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class DetailMovieItems {

    private int movieId;
    private String movieBackdrop;
    private String moviePoster;
    private String movieName;
    private String movieRated;
    private String movieCategory;
    private String movieDesc;
    private String releaseDate;

    private String crew1;
    private String crew2;
    private String job1;
    private String job2;

    private String videoTrailer;
    private String videoSource;

    private StringBuilder stringBuilder;

    public DetailMovieItems(JSONObject[] object ) {

        try {

            switch (object[3].getString("type_detail")){

                case "moviedetail":
                    System.out.println("Detail Movie");

                    int movieId = object[0].getInt("id");
                    String movieBackdrop = object[0].getString("backdrop_path");
                    String moviePoster = object[0].getString("poster_path");
                    String movieName = object[0].getString("original_title");
                    String movieRated = object[0].getString("vote_average");
                    //String movieCategory = object.getJSONArray("genres").getJSONObject(0).getString("name");
                    String movieDesc = object[0].getString("overview");
                    String releaseDate = object[0].getString("release_date");

                    Integer categoryLength = object[0].getJSONArray("genres").length();


                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < object[0].getJSONArray("genres").length(); i++) {

                        if (i == categoryLength-1) {
                            stringBuilder.append(object[0].getJSONArray("genres").getJSONObject(i).getString("name"));
                        }else{
                            stringBuilder.append(object[0].getJSONArray("genres").getJSONObject(i).getString("name")+", ");
                        }
                    }


                    if (object[1].getJSONArray("crew").length()==0){

                        String crew1 = "";
                        String crew2 = "";
                        String job1 = "";
                        String job2 = "";

                        this.crew1 = crew1;
                        this.crew2 = crew2;
                        this.job1 = job1;
                        this.job2 = job2;

                    }else if (object[1].getJSONArray("crew").length()==1){

                        String crew1 = object[1].getJSONArray("crew").getJSONObject(0).getString("name");
                        String crew2 = "";
                        String job1 = object[1].getJSONArray("crew").getJSONObject(0).getString("job");
                        String job2 = "";

                        this.crew1 = crew1;
                        this.crew2 = crew2;
                        this.job1 = job1;
                        this.job2 = job2;
                    } else if (object[1].getJSONArray("crew").length()>=2){

                        String crew1 = object[1].getJSONArray("crew").getJSONObject(0).getString("name");
                        String crew2 = object[1].getJSONArray("crew").getJSONObject(1).getString("name");
                        String job1 = object[1].getJSONArray("crew").getJSONObject(0).getString("job");
                        String job2 = object[1].getJSONArray("crew").getJSONObject(1).getString("job");

                        this.crew1 = crew1;
                        this.crew2 = crew2;
                        this.job1 = job1;
                        this.job2 = job2;
                    }

                    if (object[2].getJSONArray("results").length()==0){

                        String videoTrailer = "";
                        String videoSource = "";

                        this.videoTrailer = videoTrailer;
                        this.videoSource = videoSource;

                    } else {

                        String videoTrailer = object[2].getJSONArray("results").getJSONObject(0).getString("key");
                        String videoSource = object[2].getJSONArray("results").getJSONObject(0).getString("site");

                        this.videoTrailer = videoTrailer;
                        this.videoSource = videoSource;
                    }


                    //System.out.println(stringBuilder.toString());

                    this.movieId = movieId;
                    this.movieBackdrop = movieBackdrop;
                    this.moviePoster = moviePoster;
                    this.movieName = movieName;
                    this.movieRated = movieRated;
                    this.stringBuilder = stringBuilder;
                    this.movieDesc = movieDesc;
                    this.releaseDate = releaseDate;

                    break;


                case "tvshowdetail":
                    System.out.println("Detail TV Show");

                    int tvId = object[0].getInt("id");
                    String tvBackdrop = object[0].getString("backdrop_path");
                    String tvPoster = object[0].getString("poster_path");
                    String tvName = object[0].getString("original_name");
                    String tvRated = object[0].getString("vote_average");
                    //String movieCategory = object.getJSONArray("genres").getJSONObject(0).getString("name");
                    String tvDesc = object[0].getString("overview");
                    String tvDate = object[0].getString("first_air_date");

                    Integer tvcategoryLength = object[0].getJSONArray("genres").length();


                    StringBuilder tvstringBuilder = new StringBuilder();
                    for (int i = 0; i < object[0].getJSONArray("genres").length(); i++) {

                        if (i == tvcategoryLength-1) {
                            tvstringBuilder.append(object[0].getJSONArray("genres").getJSONObject(i).getString("name"));
                        }else{
                            tvstringBuilder.append(object[0].getJSONArray("genres").getJSONObject(i).getString("name")+", ");
                        }
                    }


                    if (object[1].getJSONArray("crew").length()==0){

                        String crew1 = "";
                        String crew2 = "";
                        String job1 = "";
                        String job2 = "";

                        this.crew1 = crew1;
                        this.crew2 = crew2;
                        this.job1 = job1;
                        this.job2 = job2;

                    }else if (object[1].getJSONArray("crew").length()==1){

                        String crew1 = object[1].getJSONArray("crew").getJSONObject(0).getString("name");
                        String crew2 = "";
                        String job1 = object[1].getJSONArray("crew").getJSONObject(0).getString("job");
                        String job2 = "";

                        this.crew1 = crew1;
                        this.crew2 = crew2;
                        this.job1 = job1;
                        this.job2 = job2;
                    } else if (object[1].getJSONArray("crew").length()>=2){

                        String crew1 = object[1].getJSONArray("crew").getJSONObject(0).getString("name");
                        String crew2 = object[1].getJSONArray("crew").getJSONObject(1).getString("name");
                        String job1 = object[1].getJSONArray("crew").getJSONObject(0).getString("job");
                        String job2 = object[1].getJSONArray("crew").getJSONObject(1).getString("job");

                        this.crew1 = crew1;
                        this.crew2 = crew2;
                        this.job1 = job1;
                        this.job2 = job2;
                    }

                    if (object[2].getJSONArray("results").length()==0){

                        String videoTrailer = "";
                        String videoSource = "";

                        this.videoTrailer = videoTrailer;
                        this.videoSource = videoSource;

                    } else {

                        String videoTrailer = object[2].getJSONArray("results").getJSONObject(0).getString("key");
                        String videoSource = object[2].getJSONArray("results").getJSONObject(0).getString("site");

                        this.videoTrailer = videoTrailer;
                        this.videoSource = videoSource;
                    }


                    //System.out.println(stringBuilder.toString());

                    this.movieId = tvId;
                    this.movieBackdrop = tvBackdrop;
                    this.moviePoster = tvPoster;
                    this.movieName = tvName;
                    this.movieRated = tvRated;
                    this.stringBuilder = tvstringBuilder;
                    this.movieDesc = tvDesc;
                    this.releaseDate = tvDate;

                    break;
            }





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


    public String getMovieBackdrop() {
        return movieBackdrop;
    }

    public void setMovieBackdrop(String movieBackdrop) {
        this.movieBackdrop = movieBackdrop;
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

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    public void setStringBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }


    public String getCrew1() {
        return crew1;
    }

    public void setCrew1(String crew1) {
        this.crew1 = crew1;
    }

    public String getCrew2() {
        return crew2;
    }

    public void setCrew2(String crew2) {
        this.crew2 = crew2;
    }

    public String getJob1() {
        return job1;
    }

    public void setJob1(String job1) {
        this.job1 = job1;
    }

    public String getJob2() {
        return job2;
    }

    public void setJob2(String job2) {
        this.job2 = job2;
    }

    public String getVideoTrailer() {
        return videoTrailer;
    }

    public void setVideoTrailer(String videoTrailer) {
        this.videoTrailer = videoTrailer;
    }

    public String getVideoSource() {
        return videoSource;
    }

    public void setVideoSource(String videoSource) {
        this.videoSource = videoSource;
    }





}
