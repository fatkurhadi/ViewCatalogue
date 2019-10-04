package com.example.viewcatalogue.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.viewcatalogue.R;

/**
 * Implementation of App Widget functionality.
 */
public class CatalogueWidget extends AppWidgetProvider {
    public static final String WIDGET_TOAST = "com.example.viewcatalogue.TOAST_ACTION";
    public static final String WIDGET_ITEM = "com.example.viewcatalogue.EXTRA_ITEM";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent widgetIntent = new Intent(context, CatalogueStackWidgetService.class);
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        widgetIntent.setData(Uri.parse(widgetIntent.toUri(Intent.URI_INTENT_SCHEME)));
        // Construct the RemoteViews object
        RemoteViews widgetRemoteViews = new RemoteViews(context.getPackageName(), R.layout.catalogue_widget);
        widgetRemoteViews.setRemoteAdapter(R.id.sv_widget, widgetIntent);
        widgetRemoteViews.setEmptyView(R.id.sv_widget, R.id.empty_widget);

        Intent widgetToastIntent = new Intent(context, CatalogueWidget.class);
        widgetToastIntent.setAction(CatalogueWidget.WIDGET_TOAST);
        widgetToastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        widgetToastIntent.setData(Uri.parse(widgetIntent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent widgetPendingIntent = PendingIntent.getBroadcast(context, 0, widgetToastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        widgetRemoteViews.setPendingIntentTemplate(R.id.sv_widget, widgetPendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, widgetRemoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null){
            if (intent.getAction().equals(WIDGET_TOAST)){
                String movieName = intent.getStringExtra(WIDGET_ITEM);
                Toast.makeText(context, movieName, Toast.LENGTH_SHORT).show();
            }
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
}

