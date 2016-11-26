package com.chiragaggarwal.bolt.run.history.list;

import android.app.LoaderManager;
import android.os.Handler;

import com.chiragaggarwal.bolt.run.persistance.RunLocalStorage;

public class HistoryPresenter {
    public static final int DELAY = 300;
    private HistoryView historyView;
    private RunLocalStorage runLocalStorage;

    public HistoryPresenter(HistoryView historyView, RunLocalStorage runLocalStorage) {
        this.historyView = historyView;
        this.runLocalStorage = runLocalStorage;
    }

    public void loadRuns(LoaderManager loaderManager) {
        new Handler().postDelayed(() -> loadRunsFromLocal(loaderManager), 300);
    }

    private void loadRunsFromLocal(LoaderManager loaderManager) {
        runLocalStorage.loadRuns(loaderManager, runs -> {
            if (runs.isEmpty()) {
                historyView.onHistoryLoadFailed();
            } else {
                historyView.onHistoryLoaded(runs);
            }
        });
    }
}
