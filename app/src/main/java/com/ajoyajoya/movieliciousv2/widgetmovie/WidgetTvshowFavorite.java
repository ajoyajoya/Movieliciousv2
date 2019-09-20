package com.ajoyajoya.movieliciousv2.widgetmovie;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.ajoyajoya.movieliciousv2.DetailMovie;
import com.ajoyajoya.movieliciousv2.R;
import com.ajoyajoya.movieliciousv2.favorite.FavoriteMovie;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetTvshowFavorite extends AppWidgetProvider {

    private static final String TOAST_ACTION = "com.ajoyajoya.movieliciousv2.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.ajoyajoya.movieliciousv2.EXTRA_ITEM";
    public static final String EXTRA_CATEGORY = "com.ajoyajoya.movieliciousv2.EXTRA_CATEGORY";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetTvService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_tvshow_favorite);
        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);
        Intent toastIntent = new Intent(context, WidgetTvshowFavorite.class);
        toastIntent.setAction(WidgetTvshowFavorite.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION)) {
                int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
                String movieTitle = intent.getStringExtra(EXTRA_CATEGORY);
                //Toast.makeText(context, "Touched view " + viewIndex +" / "+ movieTitle, Toast.LENGTH_SHORT).show();

                FavoriteMovie favoriteMovie = new FavoriteMovie();
                favoriteMovie.setMovieid(viewIndex);
                favoriteMovie.setMoviename(movieTitle);
                favoriteMovie.setTypedetail("tvshowdetail");
                Intent moveIntent = new Intent(context, DetailMovie.class);
                moveIntent.putExtra(DetailMovie.EXTRA_MOVIE_ID, favoriteMovie);
                context.startActivity(moveIntent);
            }
        }
    }
}

