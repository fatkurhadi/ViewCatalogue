package com.example.viewcatalogue.reminder;

import android.content.Context;
import android.content.SharedPreferences;

public class NotifyPreference {
    private static final String notifyPref = "notifier";
    private String dailyNotifier = "daily_notifier";
    private String releasedNotifier = "released_notifier";
    private Context notifierContext;
    private SharedPreferences notifierSharedPreferences;

    public NotifyPreference(Context notifierContext) {
        this.notifierContext = notifierContext;
    }

    public boolean getDailyNotifier() {
        notifierSharedPreferences = notifierContext.getSharedPreferences(notifyPref, Context.MODE_PRIVATE);
        return notifierSharedPreferences.getBoolean(dailyNotifier, false);
    }

    public void setDailyNotifier(Boolean dailyNotify) {
        this.dailyNotifier = dailyNotifier;
        notifierSharedPreferences = notifierContext.getSharedPreferences(notifyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor notifierEditor = notifierSharedPreferences.edit();
        notifierEditor.putBoolean(dailyNotifier, dailyNotify);
        notifierEditor.apply();
    }

    public boolean getReleasedNotifier() {
        notifierSharedPreferences = notifierContext.getSharedPreferences(notifyPref, Context.MODE_PRIVATE);
        return notifierSharedPreferences.getBoolean(releasedNotifier, false);
    }

    public void setReleasedNotifier(Boolean releasedNotify) {
        this.releasedNotifier = releasedNotifier;
        notifierSharedPreferences = notifierContext.getSharedPreferences(notifyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor notifierEditor = notifierSharedPreferences.edit();
        notifierEditor.putBoolean(releasedNotifier, releasedNotify);
        notifierEditor.apply();
    }
}
