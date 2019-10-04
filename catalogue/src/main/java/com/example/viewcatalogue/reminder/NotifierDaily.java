package com.example.viewcatalogue.reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.viewcatalogue.CatalogueMainActivity;
import com.example.viewcatalogue.R;

import java.util.Calendar;

public class NotifierDaily extends BroadcastReceiver {
    private final int NOTIFIER_ID = 1;

    public NotifierDaily() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        DailyNotifier(context);
    }

    private void DailyNotifier(Context context) {
        int ch_req = 19;
        String ch_id = "chanelFirst";
        String ch_name = "dailyNotifier";
        String ch_title = context.getString(R.string.daily_title);
        CharSequence ch_message = context.getString(R.string.daily_message);
        Intent chIntent = new Intent(context, CatalogueMainActivity.class);
        PendingIntent chPendingIntent = TaskStackBuilder.create(context)
                .addNextIntent(chIntent)
                .getPendingIntent(ch_req, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager chNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder chBuilder = new NotificationCompat.Builder(context, ch_id)
                .setContentIntent(chPendingIntent)
                .setSmallIcon(R.drawable.icon_notifications_active)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_notifications_active))
                .setContentTitle(ch_title)
                .setContentText(ch_message)
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel chNotifier = new NotificationChannel(ch_id, ch_name, NotificationManager.IMPORTANCE_DEFAULT);
            chBuilder.setChannelId(ch_id);
            if (chNotificationManager != null){
                chNotificationManager.createNotificationChannel(chNotifier);
            }
        }
        if (chNotificationManager != null){
            chNotificationManager.notify(NOTIFIER_ID, chBuilder.build());
        }
    }

    public void dailyNotifierOn(Context context){
        AlarmManager chAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent chIntent = new Intent(context, NotifierDaily.class);
        PendingIntent chPendingIntent = PendingIntent.getBroadcast(context, NOTIFIER_ID, chIntent, 0);

        Calendar chCalendar = Calendar.getInstance();
        chCalendar.set(Calendar.HOUR_OF_DAY, 7);
        chCalendar.set(Calendar.MINUTE, 0);
        chCalendar.set(Calendar.SECOND, 0);

        if (chAlarmManager != null) {
            chAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, chCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, chPendingIntent);
        }
    }

    public void dailyNotifierOff(Context context){
        AlarmManager ch_alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent ch_intent = new Intent(context, NotifierDaily.class);
        PendingIntent ch_pendingIntent = PendingIntent.getBroadcast(context, NOTIFIER_ID, ch_intent, 0);
        ch_pendingIntent.cancel();
        if (ch_alarmManager != null) {
            ch_alarmManager.cancel(ch_pendingIntent);
        }
    }
}

