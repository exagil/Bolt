package com.chiragaggarwal.bolt.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import com.chiragaggarwal.bolt.R;

public class RunsDataChangeObserver extends ContentObserver {
    private final AppWidgetManager appWidgetManager;
    private final ComponentName componentName;

    public RunsDataChangeObserver(Handler workerQueue, AppWidgetManager appWidgetManager, ComponentName componentName) {
        super(workerQueue);
        this.appWidgetManager = appWidgetManager;
        this.componentName = componentName;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_run_history);
    }
}