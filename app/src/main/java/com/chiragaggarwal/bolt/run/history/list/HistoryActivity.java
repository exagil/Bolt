package com.chiragaggarwal.bolt.run.history.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.run.Run;
import com.chiragaggarwal.bolt.run.history.detail.RunDetailsActivity;
import com.chiragaggarwal.bolt.run.persistance.RunLocalStorage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity implements HistoryView {
    private RunListAdapter runListAdapter;
    private RunLocalStorage runLocalStorage;
    private HistoryPresenter historyPresenter;

    @BindView(R.id.list_run_history)
    public RecyclerView listRunHistory;

    @BindView(R.id.text_no_runs)
    public TextView textNoRuns;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        runLocalStorage = new RunLocalStorage(this);
        historyPresenter = new HistoryPresenter(this, runLocalStorage);
        listRunHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        runListAdapter = new RunListAdapter(this, run -> launchRunDetailsActivityWith(run));
        listRunHistory.setAdapter(runListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        historyPresenter.loadRuns(getLoaderManager());
    }

    private void launchRunDetailsActivityWith(Run run) {
        Intent intent = new Intent(this, RunDetailsActivity.class);
        intent.putExtra(Run.TAG, run);
        startActivity(intent);
    }

    @Override
    public void onHistoryLoaded(List<Run> runs) {
        listRunHistory.setVisibility(View.VISIBLE);
        textNoRuns.setVisibility(View.GONE);
        runListAdapter.populate(runs);
    }

    @Override
    public void onHistoryLoadFailed() {
        listRunHistory.setVisibility(View.GONE);
        textNoRuns.setVisibility(View.VISIBLE);
    }
}
