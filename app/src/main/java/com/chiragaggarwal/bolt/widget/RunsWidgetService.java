package com.chiragaggarwal.bolt.widget;

import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViewsService;

import com.chiragaggarwal.bolt.run.Run;
import com.chiragaggarwal.bolt.run.persistance.RunLocalStorage;

import java.util.List;

public class RunsWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        List<Run> runs = new RunLocalStorage(this).loadRunsBlocking();
        return new RunsWidgetFactory(runs, this);
    }
}
