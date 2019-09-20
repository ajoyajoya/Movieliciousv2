package com.ajoyajoya.movieliciousv2.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {


    public static final String AUTHORITY = "com.ajoyajoya.movieliciousv2";
    public static final String SCHEME = "content";

    public static final String TABLE_FAVORITE = "favorite";

    public static final String TABLE_TVFAVORITE = "tvfavorite";

    public static final class FavoriteColumns implements BaseColumns {
        public static final String IDMOVIE = "idmovie";
        public static final String MOVIENAME = "moviename";
        public static final String IMAGEURL = "imageurl";
        public static final String DATE = "date";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE)
                .build();
    }

    public static final class TvFavoriteColumns implements BaseColumns {
        public static final String IDTV = "idtv";
        public static final String TVNAME = "tvname";
        public static final String IMAGEURLTV = "imageurltv";
        public static final String DATETV = "datetv";
        public static final Uri CONTENT_URITV = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TVFAVORITE)
                .build();
    }


    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

}
