package com.chiragaggarwal.bolt.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.widget.RemoteViews;

import com.chiragaggarwal.bolt.R;

public class RunsWidgetProvider extends AppWidgetProvider {
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetIdIndex = 0; appWidgetIdIndex < appWidgetIds.length; appWidgetIdIndex++) {
            int appWidgetId = appWidgetIds[appWidgetIdIndex];
            Intent intent = new Intent(context.getApplicationContext(), RunsWidgetService.class);
            RemoteViews runsRemoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_runs_appwidget);
            runsRemoteViews.setRemoteAdapter(R.id.list_run_history, intent);
            runsRemoteViews.setEmptyView(R.id.list_run_history, R.id.text_empty_runs);
            appWidgetManager.updateAppWidget(appWidgetId, runsRemoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private class RunsDataChangeObserver extends ContentObserver {
        public RunsDataChangeObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);

        }
    }
}
