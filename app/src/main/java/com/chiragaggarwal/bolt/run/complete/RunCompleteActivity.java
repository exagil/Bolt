package com.chiragaggarwal.bolt.run.complete;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.run.RunViewModel;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

public class RunCompleteActivity extends AppCompatActivity {
    private RunViewModel runViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_complete);
        ElapsedTime elapsedTime = getIntent().getParcelableExtra(ElapsedTime.TAG);
        runViewModel = new RunViewModel(getResources());
        runViewModel.setElapsedTime(elapsedTime);
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbarRunComplete = (Toolbar) findViewById(R.id.run_complete_toolbar);
        setSupportActionBar(toolbarRunComplete);
        getSupportActionBar().setTitle(runViewModel.getElapsedTime());
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.ok, (dialog, which) -> super.onBackPressed())
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setTitle(R.string.notify_dismiss)
                .show();
    }
}
