package com.ajoyajoya.movieliciousv2.settings;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.ajoyajoya.movieliciousv2.DetailMovie;
import com.ajoyajoya.movieliciousv2.R;
import com.ajoyajoya.movieliciousv2.favorite.FavoriteMovie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class GetReleaseMovie extends BroadcastReceiver {

    final String API_KEY = "6c850abf79ae2a311643afba9e9ff654";

    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    final String CURRENT_DATE = df.format(c);

    public static final String TYPE_ONE_TIME = "OneTimeAlarm";
    public static final String TYPE_REPEATING = "RepeatingAlarm";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    private static final int ID_RELEASE = 100;
    private static final int ID_REPEATING = 101;

    public GetReleaseMovie() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        String title = context.getString(R.string.title_daily);
        int notifId = type.equalsIgnoreCase(TYPE_ONE_TIME) ? ID_RELEASE : ID_REPEATING;

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&primary_release_date.gte="+CURRENT_DATE+"&primary_release_date.lte="+CURRENT_DATE;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    String movieRelease = responseObject.getJSONArray("results").getJSONObject(0).getString("original_title");
                    int idMovieRelease = responseObject.getJSONArray("results").getJSONObject(0).getInt("id");
                    String title = context.getString(R.string.release_today);
                    String message = movieRelease;
                    System.out.println(title + " / " +message);
                    int notifId = 100;

                    showToast(context, title, message);

                    showAlarmNotification(context, title, message, notifId, idMovieRelease);
                }catch (Exception e){
                    Log.d("Exception", e.getMessage());

                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // ketika proses gagal, maka jobFinished diset dengan parameter true. Yang artinya job perlu di reschedule
                Log.d("onFailure", error.getMessage());
            }
        });


    }

    private void showToast(Context context, String title, String message) {
        Toast.makeText(context, title + " : " + message, Toast.LENGTH_LONG).show();
    }

    public void showAlarmNotification(Context context, String title, String message, int notifId, int idMovie) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "AlarmManager Release";

        FavoriteMovie favoriteMovie = new FavoriteMovie();
        favoriteMovie.setMovieid(idMovie);
        favoriteMovie.setMoviename(message);
        favoriteMovie.setTypedetail("moviedetail");

        Intent intent = new Intent(context, DetailMovie.class);
        intent.putExtra(DetailMovie.EXTRA_MOVIE_ID, favoriteMovie);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setContentTitle(message)
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setContentText(title)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }

    }

    public static void setReleaseNotif(Context context, String type, String time, String message) {
        if (isDateInvalid(time, TIME_FORMAT)) return;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, GetReleaseMovie.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, context.getString(R.string.success_releaseon_notif), Toast.LENGTH_SHORT).show();
    }

    private String DATE_FORMAT = "yyyy-MM-dd";
    private static String TIME_FORMAT = "HH:mm";

    public static boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, GetReleaseMovie.class);
        int requestCode = type.equalsIgnoreCase(TYPE_ONE_TIME) ? ID_RELEASE : ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, context.getString(R.string.success_releaseoff_notif), Toast.LENGTH_SHORT).show();
    }

}
