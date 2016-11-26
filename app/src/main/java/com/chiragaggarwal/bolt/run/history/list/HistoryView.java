package com.chiragaggarwal.bolt.run.history.list;

import com.chiragaggarwal.bolt.run.Run;

import java.util.List;

public interface HistoryView {
    void onHistoryLoaded(List<Run> run);

    void onHistoryLoadFailed();
}
