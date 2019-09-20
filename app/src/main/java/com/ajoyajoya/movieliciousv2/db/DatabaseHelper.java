package com.ajoyajoya.movieliciousv2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbmovieapp";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_FAVORITE,
            DatabaseContract.FavoriteColumns._ID,
            DatabaseContract.FavoriteColumns.IDMOVIE,
            DatabaseContract.FavoriteColumns.MOVIENAME,
            DatabaseContract.FavoriteColumns.IMAGEURL,
            DatabaseContract.FavoriteColumns.DATE
    );

    private static final String SQL_CREATE_TABLE_TVFAVORITE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_TVFAVORITE,
            DatabaseContract.TvFavoriteColumns._ID,
            DatabaseContract.TvFavoriteColumns.IDTV,
            DatabaseContract.TvFavoriteColumns.TVNAME,
            DatabaseContract.TvFavoriteColumns.IMAGEURLTV,
            DatabaseContract.TvFavoriteColumns.DATETV
    );

    public DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //System.out.println(context.getDatabasePath(DatabaseHelper.DATABASE_NAME));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
        db.execSQL(SQL_CREATE_TABLE_TVFAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVORITE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TVFAVORITE);
        onCreate(db);
    }


}
