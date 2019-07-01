package com.ajoyajoya.movieliciousv2;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private int moviePoster;
    private String movieName;
    private String movieRated;
    private String movieCategory;
    private String movieDesc;

    public int getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(int moviePoster) {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.moviePoster);
        dest.writeString(this.movieName);
        dest.writeString(this.movieRated);
        dest.writeString(this.movieCategory);
        dest.writeString(this.movieDesc);
    }

    public Movie() {
    }

    private Movie(Parcel in) {
        this.moviePoster = in.readInt();
        this.movieName = in.readString();
        this.movieRated = in.readString();
        this.movieCategory = in.readString();
        this.movieDesc = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
