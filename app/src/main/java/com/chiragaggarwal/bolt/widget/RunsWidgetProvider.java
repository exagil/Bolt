package com.chiragaggarwal.bolt.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.RemoteViews;

import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema;

public class RunsWidgetProvider extends AppWidgetProvider {
    private final HandlerThread runsWidgetHandler;
    private final Handler workerQueue;

    public RunsWidgetProvider() {
        runsWidgetHandler = new HandlerThread("runs_widget_handler");
        runsWidgetHandler.start();
        workerQueue = new Handler(runsWidgetHandler.getLooper());
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, RunsWidgetProvider.class);
        RunsDataChangeObserver runsDataChangeObserver = new RunsDataChangeObserver(workerQueue, appWidgetManager, componentName);
        context.getContentResolver().registerContentObserver(BoltDatabaseSchema.RunSchema.ALL_RUNS_RESOURCE_URI, false, runsDataChangeObserver);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetIdIndex = 0; appWidgetIdIndex < appWidgetIds.length; appWidgetIdIndex++) {
            int appWidgetId = appWidgetIds[appWidgetIdIndex];
            Intent intent = new Intent(context.getApplicationContext(), RunsWidgetService.class);
            RemoteViews runsRemoteViews = new RemoteViews(context.getPackageName(), R.layout.initial_layout);
            runsRemoteViews.setRemoteAdapter(R.id.widget_list_run_history, intent);
            runsRemoteViews.setEmptyView(R.id.widget_list_run_history, R.id.layout_empty);
            appWidgetManager.updateAppWidget(appWidgetId, runsRemoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
