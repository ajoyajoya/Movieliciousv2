package com.ajoyajoya.movieliciousv2;

import org.json.JSONObject;

import java.util.ArrayList;

public class GenreItems {

    private int genreid;
    private String genrename;


    private ArrayList<Integer> genreIds;

    public GenreItems(JSONObject object) {
        try {
            int genreId = object.getInt("id");
            String genreName = object.getString("original_title");
            this.genreid = genreId;
            this.genrename = genreName;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getGenreid() {
        return genreid;
    }

    public void setGenreid(int genreid) {
        this.genreid = genreid;
    }

    public String getGenrename() {
        return genrename;
    }

    public void setGenrename(String genrename) {
        this.genrename = genrename;
    }


    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(ArrayList<Integer> genreIds) {
        this.genreIds = genreIds;
    }



}
