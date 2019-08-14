package com.ajoyajoya.movieliciousv2;

import org.json.JSONObject;

class DetailMovieItems {

    private String moviePoster;
    private String movieName;
    private String movieRated;
    //private String movieCategory;
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
                    //System.out.println("Detail Movie");

                    //int movieId = object[0].getInt("id");
                    //String movieBackdrop = object[0].getString("backdrop_path");
                    String moviePoster = object[0].getString("poster_path");
                    String movieName = object[0].getString("original_title");
                    String movieRated = object[0].getString("vote_average");
                    //String movieCategory = object.getJSONArray("genres").getJSONObject(0).getString("name");
                    String movieDesc = object[0].getString("overview");
                    String releaseDate = object[0].getString("release_date");

                    int categoryLength = object[0].getJSONArray("genres").length();


                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < object[0].getJSONArray("genres").length(); i++) {

                        if (i == categoryLength-1) {
                            stringBuilder.append(object[0].getJSONArray("genres").getJSONObject(i).getString("name"));
                        }else{
                            stringBuilder.append(object[0].getJSONArray("genres").getJSONObject(i).getString("name")).append(", ");
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

                    this.moviePoster = moviePoster;
                    this.movieName = movieName;
                    this.movieRated = movieRated;
                    this.stringBuilder = stringBuilder;
                    this.movieDesc = movieDesc;
                    this.releaseDate = releaseDate;

                    break;


                case "tvshowdetail":
                    //System.out.println("Detail TV Show");

                    //int tvId = object[0].getInt("id");
                    //String tvBackdrop = object[0].getString("backdrop_path");
                    String tvPoster = object[0].getString("poster_path");
                    String tvName = object[0].getString("original_name");
                    String tvRated = object[0].getString("vote_average");
                    //String movieCategory = object.getJSONArray("genres").getJSONObject(0).getString("name");
                    String tvDesc = object[0].getString("overview");
                    String tvDate = object[0].getString("first_air_date");

                    int tvcategoryLength = object[0].getJSONArray("genres").length();


                    StringBuilder tvstringBuilder = new StringBuilder();
                    for (int i = 0; i < object[0].getJSONArray("genres").length(); i++) {

                        if (i == tvcategoryLength-1) {
                            tvstringBuilder.append(object[0].getJSONArray("genres").getJSONObject(i).getString("name"));
                        }else{
                            tvstringBuilder.append(object[0].getJSONArray("genres").getJSONObject(i).getString("name")).append(", ");
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

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }


    public String getCrew1() {
        return crew1;
    }

    public String getCrew2() {
        return crew2;
    }

    public String getJob1() {
        return job1;
    }

    public String getJob2() {
        return job2;
    }

    public String getVideoTrailer() {
        return videoTrailer;
    }

    public String getVideoSource() {
        return videoSource;
    }


}
