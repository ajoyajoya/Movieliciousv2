package com.ajoyajoya.movieliciousv2.favorite;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteMovie implements Parcelable {


    private int id;
    private int movieid;
    private String moviename;
    private String imageurl;
    private String date;
    private String typedetail;


    private int position_list;

    public int getPosition_list() {
        return position_list;
    }

    public void setPosition_list(int position_list) {
        this.position_list = position_list;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypedetail() {
        return typedetail;
    }

    public void setTypedetail(String typedetail) {
        this.typedetail = typedetail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(movieid);
        dest.writeString(moviename);
        dest.writeString(imageurl);
        dest.writeString(date);
        dest.writeString(typedetail);
        dest.writeInt(position_list);
    }

    public FavoriteMovie() {
    }

    private FavoriteMovie(Parcel in) {
        id = in.readInt();
        movieid = in.readInt();
        moviename = in.readString();
        imageurl = in.readString();
        date = in.readString();
        typedetail = in.readString();
        position_list = in.readInt();
    }

    public static final Creator<FavoriteMovie> CREATOR = new Creator<FavoriteMovie>() {
        @Override
        public FavoriteMovie createFromParcel(Parcel in) {
            return new FavoriteMovie(in);
        }

        @Override
        public FavoriteMovie[] newArray(int size) {
            return new FavoriteMovie[size];
        }
    };
}
