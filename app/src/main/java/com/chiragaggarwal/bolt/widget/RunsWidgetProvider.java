package com.chiragaggarwal.bolt.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.RemoteViews;

import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.run.ongoing.RunActivity;
import com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema;

public class RunsWidgetProvider extends AppWidgetProvider {
    private static final int CODE_START_RUN = 1;
    private final Handler workerQueue;

    public RunsWidgetProvider() {
        HandlerThread runsWidgetHandler = new HandlerThread("runs_widget_handler");
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
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context.getApplicationContext(), RunsWidgetService.class);
            RemoteViews runsRemoteViews = new RemoteViews(context.getPackageName(), R.layout.initial_layout);
            runsRemoteViews.setRemoteAdapter(R.id.widget_list_run_history, intent);
            runsRemoteViews.setEmptyView(R.id.widget_list_run_history, R.id.layout_empty);
            runsRemoteViews.setOnClickPendingIntent(R.id.layout_empty, startRunPendingIntent(context));
            appWidgetManager.updateAppWidget(appWidgetId, runsRemoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private PendingIntent startRunPendingIntent(Context context) {
        Intent intent = new Intent(context, RunActivity.class);
        return PendingIntent.getActivity(context, CODE_START_RUN, intent, PendingIntent.FLAG_CANCEL_CURRENT, new Bundle());
    }
}
