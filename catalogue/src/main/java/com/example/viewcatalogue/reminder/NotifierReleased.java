package com.example.viewcatalogue.reminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.viewcatalogue.BuildConfig;
import com.example.viewcatalogue.CatalogueMainActivity;
import com.example.viewcatalogue.CatalogueReleasedActivity;
import com.example.viewcatalogue.R;
import com.example.viewcatalogue.helper.ModelMovie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class NotifierReleased extends BroadcastReceiver {
    private final int NOTIFIER_ID = 2;
    private ArrayList<ModelMovie> modelMovies = new ArrayList<>();

    public NotifierReleased() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        getReleased(context);
    }

    private void getReleased(final Context context) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
        final String today = day.format(new Date());
        AsyncHttpClient releasedClient = new AsyncHttpClient();
        String releasedUrl = BuildConfig.MOVIE_URL_BASE + BuildConfig.MY_API_KEY + "&primary_release_date.gte=" + today + "&primary_release_date.lte=" + today;
        releasedClient.get(releasedUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String releasedResult = new String(responseBody);
                    JSONObject releasedObject = new JSONObject(releasedResult);
                    JSONArray releasedList = releasedObject.getJSONArray("result");
                    for (int i = 0; i < releasedList.length(); i++) {
                        JSONObject movie = releasedList.getJSONObject(i);
                        if (today.equals(movie.getString("release_date"))) {
                            ModelMovie m = new ModelMovie();
                            String id = String.valueOf(movie.getInt("id"));
                            m.setMovie_id(id);
                            modelMovies.add(m);
                        }
                    }
                    releasedShow(context);
                } catch (Exception er){
                    Log.d("ReleasedNotifier : " , er.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailConnection : ", error.getMessage());
            }
        });
    }

    private void releasedShow(Context context) {
        int ch_req = 28;
        String ch_id = "chanelSecond";
        String ch_name = "releasedNotifier";
        String ch_title = context.getString(R.string.released_title);
        String ch_message;
        Intent chIntent;
        Bitmap bitmapImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_notifications_active);
        NotificationManager chNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder chBuilder = new NotificationCompat.Builder(context, ch_id);
        if (modelMovies.size() > 0) {
            chIntent = new Intent(context, CatalogueReleasedActivity.class);
            PendingIntent chPendingIntent = TaskStackBuilder.create(context).addNextIntent(chIntent).getPendingIntent(ch_req, PendingIntent.FLAG_UPDATE_CURRENT);
            ch_message = context.getString(R.string.released_message);
            chBuilder.setSmallIcon(R.drawable.icon_notifications_active)
                    .setLargeIcon(bitmapImage)
                    .setContentTitle(ch_title)
                    .setContentText(ch_message)
                    .setContentIntent(chPendingIntent)
                    .setAutoCancel(true);
            if (chNotificationManager != null) {
                chNotificationManager.notify(NOTIFIER_ID, chBuilder.build());
            }
        } else {
            chIntent = new Intent(context, CatalogueMainActivity.class);
            PendingIntent ch_pendingIntent = TaskStackBuilder.create(context).addNextIntent(chIntent).getPendingIntent(ch_req, PendingIntent.FLAG_UPDATE_CURRENT);
            ch_message = context.getString(R.string.released_empty);
            chBuilder.setSmallIcon(R.drawable.icon_notifications_active)
                    .setLargeIcon(bitmapImage)
                    .setContentTitle(ch_title)
                    .setContentText(ch_message)
                    .setContentIntent(ch_pendingIntent)
                    .setAutoCancel(true);
            if (chNotificationManager != null) {
                chNotificationManager.notify(NOTIFIER_ID, chBuilder.build());
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel chNotifier = new NotificationChannel(ch_id, ch_name, NotificationManager.IMPORTANCE_DEFAULT);
            chBuilder.setChannelId(ch_id);
            if (chNotificationManager != null){
                chNotificationManager.createNotificationChannel(chNotifier);
            }
        }
    }

    public void releasedNotifierOn(Context context){
        AlarmManager chAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent chIntent = new Intent(context, NotifierReleased.class);
        PendingIntent chPendingIntent = PendingIntent.getBroadcast(context, NOTIFIER_ID, chIntent, 0);

        Calendar chCalendar = Calendar.getInstance();
        chCalendar.set(Calendar.HOUR_OF_DAY, 8);
        chCalendar.set(Calendar.MINUTE, 0);
        chCalendar.set(Calendar.SECOND, 0);

        if (chAlarmManager != null) {
            chAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, chCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, chPendingIntent);
        }
    }

    public void releasedNotifierOff(Context context){
        AlarmManager ch_alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent ch_intent = new Intent(context, NotifierReleased.class);
        PendingIntent ch_pendingIntent = PendingIntent.getBroadcast(context, NOTIFIER_ID, ch_intent, 0);
        ch_pendingIntent.cancel();
        if (ch_alarmManager != null) {
            ch_alarmManager.cancel(ch_pendingIntent);
        }
    }
}
