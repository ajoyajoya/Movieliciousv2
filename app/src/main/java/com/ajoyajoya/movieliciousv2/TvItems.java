package com.ajoyajoya.movieliciousv2;

import org.json.JSONObject;

class TvItems {

    private int TvId;
    private String TvPoster;
    private String TvName;
    private String TvRated;
    private String TvReleaseDate;

    public TvItems(JSONObject object) {
        try {
            int TvId = object.getInt("id");
            String TvPoster = object.getString("poster_path");
            String TvName = object.getString("original_name");
            String TvRated = object.getString("vote_average");
            String TvReleaseDate = object.getString("first_air_date");

            this.TvId = TvId;
            this.TvPoster = TvPoster;
            this.TvName = TvName;
            this.TvRated = TvRated;
            this.TvReleaseDate = TvReleaseDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTvId() {
        return TvId;
    }

    public String getTvPoster() {
        return TvPoster;
    }

    public String getTvName() {
        return TvName;
    }

    public String getTvRated() {
        return TvRated;
    }

    public String getTvReleaseDate() {
        return TvReleaseDate;
    }


}
