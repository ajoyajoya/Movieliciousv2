package com.ajoyajoya.movieliciousv2;

import org.json.JSONObject;

public class TvItems {

    private int TvId;
    private String TvPoster;
    private String TvName;
    private String TvRated;
    private String TvDesc;
    private String TvReleaseDate;

    public TvItems(JSONObject object) {
        try {
            int TvId = object.getInt("id");
            String TvPoster = object.getString("poster_path");
            String TvName = object.getString("original_name");
            String TvRated = object.getString("vote_average");
            String TvDesc = object.getString("overview");
            String TvReleaseDate = object.getString("first_air_date");

            this.TvId = TvId;
            this.TvPoster = TvPoster;
            this.TvName = TvName;
            this.TvRated = TvRated;
            this.TvDesc = TvDesc;
            this.TvReleaseDate = TvReleaseDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTvId() {
        return TvId;
    }

    public void setTvId(int tvId) {
        TvId = tvId;
    }

    public String getTvPoster() {
        return TvPoster;
    }

    public void setTvPoster(String tvPoster) {
        TvPoster = tvPoster;
    }

    public String getTvName() {
        return TvName;
    }

    public void setTvName(String tvName) {
        TvName = tvName;
    }

    public String getTvRated() {
        return TvRated;
    }

    public void setTvRated(String tvRated) {
        TvRated = tvRated;
    }

    public String getTvDesc() {
        return TvDesc;
    }

    public void setTvDesc(String tvDesc) {
        TvDesc = tvDesc;
    }

    public String getTvReleaseDate() {
        return TvReleaseDate;
    }

    public void setTvReleaseDate(String tvReleaseDate) {
        TvReleaseDate = tvReleaseDate;
    }


}
