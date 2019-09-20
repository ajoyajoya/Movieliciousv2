package com.ajoyajoya.movieliciousv2.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ajoyajoya.movieliciousv2.db.TvShowHelper;
import com.ajoyajoya.movieliciousv2.favorite.FavoriteTvshowFragment;

import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.AUTHORITY;
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.TABLE_TVFAVORITE;
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.TvFavoriteColumns.CONTENT_URITV;

public class TvShowProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private TvShowHelper tvShowHelper;

    static {
        // content://com.ajoyajoya.movieliciouser/movie
        //sUriMatcher.addURI(AUTHORITY, TABLE_NAME, NOTE);
        sUriMatcher.addURI(AUTHORITY, TABLE_TVFAVORITE, MOVIE);

        // content://com.ajoyajoya.movieliciouser/movie/id
        sUriMatcher.addURI(AUTHORITY, TABLE_TVFAVORITE + "/#", MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        tvShowHelper = TvShowHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        tvShowHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = tvShowHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = tvShowHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        tvShowHelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = tvShowHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URITV, new FavoriteTvshowFragment.DataObserver(new Handler(), getContext()));
        return Uri.parse(CONTENT_URITV + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        tvShowHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = tvShowHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URITV, new FavoriteTvshowFragment.DataObserver(new Handler(), getContext()));
        return deleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
