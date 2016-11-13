package com.chiragaggarwal.bolt.run.persistance;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.chiragaggarwal.bolt.common.OnSuccessCallback;
import com.chiragaggarwal.bolt.run.Run;

import java.util.ArrayList;
import java.util.List;

public class RunsLoader implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID = 0;
    private Context context;
    private LoaderManager loaderManager;
    private OnSuccessCallback<List<Run>> onSuccessCallback;

    public RunsLoader(Context context, LoaderManager loaderManager, OnSuccessCallback<List<Run>> onSuccessCallback) {
        this.context = context;
        this.loaderManager = loaderManager;
        this.onSuccessCallback = onSuccessCallback;
    }

    public void load() {
        loaderManager.initLoader(ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(context, BoltDatabaseSchema.RunSchema.ALL_RUNS_RESOURCE_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<Run> runs = loadRuns(data);
        onSuccessCallback.onSuccess(runs);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private List<Run> loadRuns(Cursor runsCursor) {
        boolean doRunsExist = runsCursor.moveToFirst();
        ArrayList<Run> runs = new ArrayList<>();
        if (doRunsExist) {
            for (int runCursorIndex = 0; runCursorIndex < runsCursor.getCount(); runCursorIndex++) {
                runsCursor.moveToPosition(runCursorIndex);
                runs.add(Run.fromCursor(runsCursor));
            }
        }
        return runs;
    }
}
