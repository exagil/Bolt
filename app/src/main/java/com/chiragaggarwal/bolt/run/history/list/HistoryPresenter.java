package com.chiragaggarwal.bolt.run.history.list;

import android.app.LoaderManager;

import com.chiragaggarwal.bolt.run.persistance.RunLocalStorage;

public class HistoryPresenter {
    private HistoryView historyView;
    private RunLocalStorage runLocalStorage;

    public HistoryPresenter(HistoryView historyView, RunLocalStorage runLocalStorage) {
        this.historyView = historyView;
        this.runLocalStorage = runLocalStorage;
    }

    public void loadRuns(LoaderManager loaderManager) {
        runLocalStorage.loadRuns(loaderManager, runs -> {
            if (runs.isEmpty()) {
                historyView.onHistoryLoadFailed();
            } else {
                historyView.onHistoryLoaded(runs);
            }
        });
    }
}
