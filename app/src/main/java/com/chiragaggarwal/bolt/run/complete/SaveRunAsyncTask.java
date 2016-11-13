package com.chiragaggarwal.bolt.run.complete;

import android.os.AsyncTask;

import com.chiragaggarwal.bolt.common.OnSuccessCallback;
import com.chiragaggarwal.bolt.run.Run;
import com.chiragaggarwal.bolt.run.persistance.RunLocalStorage;

public class SaveRunAsyncTask extends AsyncTask<Run, Void, Void> {
    private RunLocalStorage runLocalStorage;
    private OnSuccessCallback<Void> onSuccessCallback;

    public SaveRunAsyncTask(RunLocalStorage runLocalStorage, OnSuccessCallback<Void> onSuccessCallback) {
        this.runLocalStorage = runLocalStorage;
        this.onSuccessCallback = onSuccessCallback;
    }

    @Override
    protected Void doInBackground(Run... runs) {
        Run run = runs[0];
        runLocalStorage.insertRun(run);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (!isCancelled())
            onSuccessCallback.onSuccess(aVoid);
    }
}
