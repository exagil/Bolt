package com.chiragaggarwal.bolt.run.history.list;

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
    private OnRunClickListener onRunClickListener;

    public RunListAdapter(Context context, OnRunClickListener onRunClickListener) {
        this.context = context;
        this.onRunClickListener = onRunClickListener;
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
        holder.setOnClickListener(v -> onRunClickListener.onClick(run));
        RunViewModel runViewModel = new RunViewModel(context.getResources());
        runViewModel.setElapsedTime(run.elapsedTimeInSeconds);
        holder.bind(run.formattedTotalDistanceInKilometers(), runViewModel.getElapsedTime(), run.formattedDate());
    }

    @Override
    public int getItemCount() {
        return runList.size();
    }

    public void populate(List<Run> runs) {
        this.runList.clear();
        this.runList.addAll(runs);
        notifyDataSetChanged();
    }
}
