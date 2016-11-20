package com.chiragaggarwal.bolt.run.history;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.run.Run;
import com.chiragaggarwal.bolt.run.persistance.RunLocalStorage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity {
    private RunListAdapter runListAdapter;
    private RunLocalStorage runLocalStorage;

    @BindView(R.id.list_run_history)
    public RecyclerView listRunHistory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        runLocalStorage = new RunLocalStorage(this);
        listRunHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        runListAdapter = new RunListAdapter(this, run -> launchRunDetailsActivityWith(run));
        listRunHistory.setAdapter(runListAdapter);
    }

    private void launchRunDetailsActivityWith(Run run) {
        Intent intent = new Intent(this, RunDetailsActivity.class);
        intent.putExtra(Run.TAG, run);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        runLocalStorage.loadRuns(getLoaderManager(), runs -> runListAdapter.populate(runs));
    }
}
