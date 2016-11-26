package com.chiragaggarwal.bolt.run.persistance;

import android.app.LoaderManager;
import android.content.Context;
import android.net.Uri;

import com.chiragaggarwal.bolt.common.OnSuccessCallback;
import com.chiragaggarwal.bolt.run.Run;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.RunSchema;

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
        return true;
    }

    public void loadRuns(LoaderManager loaderManager, OnSuccessCallback<List<Run>> onSuccessCallback) {
        Observable.timer(300, TimeUnit.MILLISECONDS)
                .doOnEach(longNotification -> new RunsLoader(context, loaderManager, onSuccessCallback).load())
                .subscribe();

    }

    public List<Run> loadRunsBlocking() {
        return new RunsLoader(context).loadBlocking();
    }
}
