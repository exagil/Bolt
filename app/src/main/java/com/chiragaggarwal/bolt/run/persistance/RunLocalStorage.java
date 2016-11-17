package com.chiragaggarwal.bolt.run.persistance;

import android.app.LoaderManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.chiragaggarwal.bolt.common.OnSuccessCallback;
import com.chiragaggarwal.bolt.run.Run;
import com.chiragaggarwal.bolt.widget.RunsWidgetProvider;

import java.util.List;

import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.RunSchema;
import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.UserLocationsSchema;

public class RunLocalStorage {
    private Context context;

    public RunLocalStorage(Context context) {
        this.context = context;
    }

    public boolean insertRun(Run run) {
        Uri resultUri = context.getContentResolver().insert(RunSchema.ALL_RUNS_RESOURCE_URI, run.persistable());
        String runRowNumber = resultUri.getLastPathSegment();
        if (runRowNumber.equals(Dao.INVALID_PATH_ROW)) {
            return false;
        }
        Long runRowNumberValue = new Long(runRowNumber);
        context.getContentResolver().bulkInsert(UserLocationsSchema.ALL_USER_LOCATIONS_URI, run.persistableUserLocations(runRowNumberValue.longValue()));
        updateWidgets();
        return true;
    }

    private void updateWidgets() {
        Intent intent = new Intent();
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, RunsWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        context.sendBroadcast(intent);
    }

    public void loadRuns(LoaderManager loaderManager, OnSuccessCallback<List<Run>> onSuccessCallback) {
        new RunsLoader(context, loaderManager, onSuccessCallback).load();
    }

    public List<Run> loadRunsBlocking() {
        return new RunsLoader(context).loadBlocking();
    }
}
