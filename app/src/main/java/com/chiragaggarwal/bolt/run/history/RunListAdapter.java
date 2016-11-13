package com.chiragaggarwal.bolt.run.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.run.Run;
import com.chiragaggarwal.bolt.run.RunViewModel;

import java.util.ArrayList;
import java.util.List;

public class RunListAdapter extends RecyclerView.Adapter<RunViewHolder> {
    private final ArrayList<Run> runList;
    private Context context;

    public RunListAdapter(Context context) {
        this.context = context;
        this.runList = new ArrayList<>();
    }

    @Override
    public RunViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View listItemRun = LayoutInflater.from(context).inflate(R.layout.list_item_run, parent, false);
        return new RunViewHolder(listItemRun);
    }

    @Override
    public void onBindViewHolder(RunViewHolder holder, int position) {
        Run run = runList.get(position);
        RunViewModel runViewModel = new RunViewModel(context.getResources());
        runViewModel.setElapsedTime(run.elapsedTimeInSeconds);
        holder.bind("0.0 km", runViewModel.getElapsedTime(), run.formattedDate());
    }

    @Override
    public int getItemCount() {
        return runList.size();
    }

    public void populate(List<Run> runs) {
        this.runList.addAll(runs);
        notifyDataSetChanged();
    }
}
