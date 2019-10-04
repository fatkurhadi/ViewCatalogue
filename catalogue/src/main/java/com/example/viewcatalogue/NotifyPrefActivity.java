package com.example.viewcatalogue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.viewcatalogue.reminder.NotifierDaily;
import com.example.viewcatalogue.reminder.NotifierReleased;
import com.example.viewcatalogue.reminder.NotifyPreference;

public class NotifyPrefActivity extends AppCompatActivity {
    private NotifyPreference notifyPreference;
    private NotifierDaily notifierDaily = new NotifierDaily();
    private NotifierReleased notifierReleased = new NotifierReleased();
    private Switch swDaily, swReleased;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_pref);
        swDaily = findViewById(R.id.sw_daily);
        swReleased = findViewById(R.id.sw_released);
        notifyPreference = new NotifyPreference(this);
        boolean notifyDaily = notifyPreference.getDailyNotifier();
        boolean notifyReleased = notifyPreference.getReleasedNotifier();
        swDaily.setChecked(notifyDaily);
        swReleased.setChecked(notifyReleased);
        swDaily.setOnClickListener(dailyListen);
        swReleased.setOnClickListener(releasedListen);
        if (swDaily.isChecked()){
            ImageView imageNotifyDaily = findViewById(R.id.img_daily);
            imageNotifyDaily.setImageDrawable(getResources().getDrawable(R.drawable.icon_notifications_active));
        } else {
            ImageView imageNotifyDaily = findViewById(R.id.img_daily);
            imageNotifyDaily.setImageDrawable(getResources().getDrawable(R.drawable.icon_notifications_off));
        }
        if (swReleased.isChecked()){
            ImageView imageNotifyReleased = findViewById(R.id.img_release);
            imageNotifyReleased.setImageDrawable(getResources().getDrawable(R.drawable.icon_notifications_active));
        } else {
            ImageView imageNotifyReleased = findViewById(R.id.img_release);
            imageNotifyReleased.setImageDrawable(getResources().getDrawable(R.drawable.icon_notifications_off));
        }
    }

    private View.OnClickListener dailyListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            notifyPreference.setDailyNotifier(swDaily.isChecked());
            boolean notifyDily = notifyPreference.getDailyNotifier();
            if (notifyDily){
                NotifyPrefActivity.this.notifierDaily.dailyNotifierOn(getApplicationContext());
                ImageView imageNotifyDaily = findViewById(R.id.img_daily);
                imageNotifyDaily.setImageDrawable(getResources().getDrawable(R.drawable.icon_notifications_active));
            } else {
                NotifyPrefActivity.this.notifierDaily.dailyNotifierOff(getApplicationContext());
                ImageView imageNotifyDaily = findViewById(R.id.img_daily);
                imageNotifyDaily.setImageDrawable(getResources().getDrawable(R.drawable.icon_notifications_off));
            }
        }
    };

    private View.OnClickListener releasedListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            notifyPreference.setReleasedNotifier(swReleased.isChecked());
            boolean notifyReleased = notifyPreference.getReleasedNotifier();
            if (notifyReleased){
                NotifyPrefActivity.this.notifierReleased.releasedNotifierOn(getApplicationContext());
                ImageView imageNotifyReleased = findViewById(R.id.img_release);
                imageNotifyReleased.setImageDrawable(getResources().getDrawable(R.drawable.icon_notifications_active));
            } else {
                NotifyPrefActivity.this.notifierReleased.releasedNotifierOff(getApplicationContext());
                ImageView imageNotifyReleased = findViewById(R.id.img_release);
                imageNotifyReleased.setImageDrawable(getResources().getDrawable(R.drawable.icon_notifications_off));
            }
        }
    };
}
