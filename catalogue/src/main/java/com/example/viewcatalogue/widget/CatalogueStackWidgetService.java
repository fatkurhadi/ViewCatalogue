package com.example.viewcatalogue.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class CatalogueStackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CatalogueStackRemoteViewsFactory(this.getApplicationContext());
    }
}
