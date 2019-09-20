package com.ajoyajoya.movieliciousv2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ajoyajoya.movieliciousv2.favorite.FavoriteMovie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.TABLE_TVFAVORITE;
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.TvFavoriteColumns.DATETV;
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.TvFavoriteColumns.IDTV;
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.TvFavoriteColumns.IMAGEURLTV;
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.TvFavoriteColumns.TVNAME;


public class TvShowHelper {

    private static final String DATABASE_TABLE = TABLE_TVFAVORITE;
    private static DatabaseHelper dataBaseHelper;
    private static TvShowHelper INSTANCE;

    private static SQLiteDatabase database;

    public TvShowHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static TvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }


    public ArrayList<FavoriteMovie> getAllFavoriteTvshow(){
        ArrayList<FavoriteMovie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null);
        cursor.moveToFirst();
        FavoriteMovie favoriteMovie;
        if (cursor.getCount() > 0){
            do{
                favoriteMovie = new FavoriteMovie();
                favoriteMovie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favoriteMovie.setMovieid(cursor.getInt(cursor.getColumnIndexOrThrow(IDTV)));
                favoriteMovie.setMoviename(cursor.getString(cursor.getColumnIndexOrThrow(TVNAME)));
                favoriteMovie.setImageurl(cursor.getString(cursor.getColumnIndexOrThrow(IMAGEURLTV)));
                favoriteMovie.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATETV)));

                arrayList.add(favoriteMovie);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertFavoriteTv(FavoriteMovie favoriteMovie) {
        ContentValues args = new ContentValues();
        args.put(IDTV, favoriteMovie.getMovieid());
        args.put(TVNAME, favoriteMovie.getMoviename());
        args.put(IMAGEURLTV, favoriteMovie.getImageurl());
        args.put(DATETV, favoriteMovie.getDate());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteFavoriteTv(int id) {
        return database.delete(DATABASE_TABLE, _ID + " = '" + id + "'", null);
    }

    public int deleteFavoriteSingleTv(int id) {
        return database.delete(DATABASE_TABLE, IDTV + " = '" + id + "'", null);

    }

    public Boolean getSingleFavoriteTvShow(String idMovie){
        ArrayList<FavoriteMovie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, new String[]{IDTV},
                IDTV + "=" + idMovie,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        System.out.println(cursor.getCount());
        if (cursor.getCount() > 0){
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , IDTV + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, IDTV + " = ?", new String[]{id});
    }

}
