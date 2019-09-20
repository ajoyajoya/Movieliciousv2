package com.ajoyajoya.movieliciousv2.widgetmovie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ajoyajoya.movieliciousv2.R;
import com.ajoyajoya.movieliciousv2.db.DatabaseHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.TABLE_TVFAVORITE;
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.TvFavoriteColumns.IDTV;
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.TvFavoriteColumns.IMAGEURLTV;
import static com.ajoyajoya.movieliciousv2.db.DatabaseContract.TvFavoriteColumns.TVNAME;

public class StackRemoteTvViewsFactory implements RemoteViewsService.RemoteViewsFactory  {

    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final List<String> mWidgetTitle = new ArrayList<>();
    private final List<Integer> mWidgetId = new ArrayList<>();
    private final Context mContext;
    private static final String DATABASE_TABLE = TABLE_TVFAVORITE;

    private static SQLiteDatabase database;

    StackRemoteTvViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        final long identityToken = Binder.clearCallingIdentity();

        DatabaseHelper dataBaseHelper = new DatabaseHelper(mContext);

        database = dataBaseHelper.getWritableDatabase();

        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0){
            do{

                try {
                    URL url = new URL("https://image.tmdb.org/t/p/original" + cursor.getString(cursor.getColumnIndexOrThrow(IMAGEURLTV)));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    mWidgetItems.add(BitmapFactory.decodeStream(input));

                } catch (IOException e) {
                    // Log exception
                    System.out.println("Error" + e);
                }

                mWidgetTitle.add(cursor.getString(cursor.getColumnIndexOrThrow(TVNAME)));
                mWidgetId.add(cursor.getInt(cursor.getColumnIndexOrThrow(IDTV)));

                System.out.println(cursor.getString(cursor.getColumnIndexOrThrow(IMAGEURLTV)) + " / " + cursor.getString(cursor.getColumnIndexOrThrow(TVNAME)) + " / " + cursor.getString(cursor.getColumnIndexOrThrow(IDTV)));

                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();

        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_tvshow_favitem);
        rv.setImageViewBitmap(R.id.img_item_thumbphoto, mWidgetItems.get(position));
        rv.setTextViewText(R.id.tv_item_title, mWidgetTitle.get(position));
        Bundle extras = new Bundle();
        extras.putInt(WidgetMovieFavorite.EXTRA_ITEM, mWidgetId.get(position));
        extras.putString(WidgetMovieFavorite.EXTRA_CATEGORY, mWidgetTitle.get(position));
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.img_item_thumbphoto, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
