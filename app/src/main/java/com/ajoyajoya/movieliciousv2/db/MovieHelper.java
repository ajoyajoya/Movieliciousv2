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
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.FavoriteColumns.DATE;
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.FavoriteColumns.IDMOVIE;
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.FavoriteColumns.IMAGEURL;
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.FavoriteColumns.MOVIENAME;
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.TABLE_FAVORITE;

public class MovieHelper {

    private static final String DATABASE_TABLE = TABLE_FAVORITE;
    private static DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

    public MovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
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


    public ArrayList<FavoriteMovie> getAllFavoriteMovies(){
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
        //System.out.println(cursor.getCount());
        if (cursor.getCount() > 0){
            do{
                favoriteMovie = new FavoriteMovie();
                favoriteMovie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favoriteMovie.setMovieid(cursor.getInt(cursor.getColumnIndexOrThrow(IDMOVIE)));
                favoriteMovie.setMoviename(cursor.getString(cursor.getColumnIndexOrThrow(MOVIENAME)));
                favoriteMovie.setImageurl(cursor.getString(cursor.getColumnIndexOrThrow(IMAGEURL)));
                favoriteMovie.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));

                //System.out.println(cursor.getInt(cursor.getColumnIndexOrThrow(_ID))+"++"+cursor.getInt(cursor.getColumnIndexOrThrow(IDMOVIE))+"++"+cursor.getString(cursor.getColumnIndexOrThrow(MOVIENAME))+"++"+cursor.getString(cursor.getColumnIndexOrThrow(IMAGEURL)));

                arrayList.add(favoriteMovie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertFavoriteMovie(FavoriteMovie favoriteMovie) {
        ContentValues args = new ContentValues();
        //System.out.println("Masukan SQL "+favoriteMovie.getMovieid()+favoriteMovie.getMoviename()+favoriteMovie.getImageurl()+favoriteMovie.getDate());
        args.put(IDMOVIE, favoriteMovie.getMovieid());
        args.put(MOVIENAME, favoriteMovie.getMoviename());
        args.put(IMAGEURL, favoriteMovie.getImageurl());
        args.put(DATE, favoriteMovie.getDate());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteFavoriteMovie(int id) {
        return database.delete(DATABASE_TABLE, _ID + " = '" + id + "'", null);

    }

    public int deleteFavoriteSingleMovie(int id) {
        return database.delete(DATABASE_TABLE, IDMOVIE + " = '" + id + "'", null);

    }

    public Boolean getSingleFavoriteMovies(String idMovie){

        Cursor cursor = database.query(DATABASE_TABLE, new String[]{IDMOVIE},
                IDMOVIE + "=" + idMovie,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        //System.out.println(cursor.getCount());
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
                , IDMOVIE + " = ?"
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
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteProvider(String id) {
        System.out.println(id);
        return database.delete(DATABASE_TABLE,IDMOVIE + " = ?", new String[]{id});
    }



}
