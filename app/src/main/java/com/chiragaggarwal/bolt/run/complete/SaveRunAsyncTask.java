package com.chiragaggarwal.bolt.run.complete;

import android.os.AsyncTask;

import com.chiragaggarwal.bolt.common.OnSuccessCallback;
import com.chiragaggarwal.bolt.run.Run;
import com.chiragaggarwal.bolt.run.persistance.RunLocalStorage;

public class SaveRunAsyncTask extends AsyncTask<Run, Void, Void> {
    private RunLocalStorage runLocalStorage;
    private OnSuccessCallback onSuccessCallback;

    public SaveRunAsyncTask(RunLocalStorage runLocalStorage, OnSuccessCallback onSuccessCallback) {
        this.runLocalStorage = runLocalStorage;
        this.onSuccessCallback = onSuccessCallback;
    }

    @Override
    protected Void doInBackground(Run... params) {
        runLocalStorage.insertRun(params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (!isCancelled())
            onSuccessCallback.onSuccess();
    }
}
