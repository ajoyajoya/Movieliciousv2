package com.ajoyajoya.movieliciousv2.db;

import android.provider.BaseColumns;

class DatabaseContract {

    static final String TABLE_FAVORITE = "favorite";

    static final String TABLE_TVFAVORITE = "tvfavorite";

    static final class FavoriteColumns implements BaseColumns {
        static final String IDMOVIE = "idmovie";
        static final String MOVIENAME = "moviename";
        static final String IMAGEURL = "imageurl";
        static final String DATE = "date";
    }

    static final class TvFavoriteColumns implements BaseColumns {
        static final String IDTV = "idtv";
        static final String TVNAME = "tvname";
        static final String IMAGEURLTV = "imageurltv";
        static final String DATETV = "datetv";
    }

}
