package com.chiragaggarwal.bolt.widget;

import android.app.LoaderManager;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.run.Run;
import com.chiragaggarwal.bolt.run.RunViewModel;
import com.chiragaggarwal.bolt.run.persistance.RunLocalStorage;
import com.chiragaggarwal.bolt.run.persistance.RunsLoader;

import java.util.List;

public class RunsWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<Run> runs;
    private Context context;

    public RunsWidgetFactory(List<Run> runs, Context context) {
        this.runs = runs;
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        runs = new RunLocalStorage(context).loadRunsBlocking();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return runs.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Run run = runs.get(position);
        RunViewModel runViewModel = new RunViewModel(context.getResources());
        runViewModel.setElapsedTime(run.elapsedTimeInSeconds);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_item_run);
        remoteViews.setTextViewText(R.id.text_date, run.formattedDate());
        remoteViews.setTextViewText(R.id.text_elapsed_time, runViewModel.getElapsedTime());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
