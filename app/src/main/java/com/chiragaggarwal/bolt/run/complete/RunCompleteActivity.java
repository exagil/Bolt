package com.chiragaggarwal.bolt.run.complete;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.RatingBar;

import com.chiragaggarwal.bolt.BoltApplication;
import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.run.Run;
import com.chiragaggarwal.bolt.run.RunViewModel;
import com.chiragaggarwal.bolt.run.history.HistoryActivity;
import com.chiragaggarwal.bolt.run.persistance.RunLocalStorage;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

import javax.inject.Inject;

public class RunCompleteActivity extends AppCompatActivity implements RunCompleteView {
    private RunViewModel runViewModel;
    private RatingBar ratingBar;
    private EditText inputRunNote;
    private UserLocations userLocations;
    private ElapsedTime elapsedTime;
    private FloatingActionButton floatingActionButton;
    private RunCompletePresenter runCompletePresenter;

    @Inject
    public RunLocalStorage runLocalStorage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BoltApplication) getApplication()).getBoltComponent().inject(this);
        setContentView(R.layout.activity_run_complete);
        runCompletePresenter = new RunCompletePresenter(this);
        initialiseView();
        initialiseViewListeners();

        elapsedTime = getIntent().getParcelableExtra(ElapsedTime.TAG);
        userLocations = getIntent().getParcelableExtra(UserLocations.TAG);
        runViewModel = new RunViewModel(getResources());
        runViewModel.setElapsedTime(elapsedTime);
        setupToolbar();
    }

    private void initialiseViewListeners() {
        floatingActionButton.setOnClickListener((view) -> saveRun());
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.ok, (dialog, which) -> super.onBackPressed())
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setTitle(R.string.notify_dismiss)
                .show();
    }

    public void saveRun() {
        int ratingBarNumStars = ratingBar.getNumStars();
        String note = inputRunNote.getText().toString();
        runCompletePresenter.saveRun(ratingBarNumStars, note, userLocations, elapsedTime);
    }

    private void onSaveComplete() {
        Intent historyActivityIntent = new Intent(this, HistoryActivity.class);
        startActivity(historyActivityIntent);
        finish();
    }

    private void initialiseView() {
        ratingBar = (RatingBar) findViewById(R.id.run_rating);
        inputRunNote = (EditText) findViewById(R.id.run_note);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_complete_run);
    }

    private void setupToolbar() {
        Toolbar toolbarRunComplete = (Toolbar) findViewById(R.id.run_complete_toolbar);
        setSupportActionBar(toolbarRunComplete);
        getSupportActionBar().setTitle(runViewModel.getElapsedTime());
    }

    @Override
    public void saveRun(int ratingBarNumStars, String note, UserLocations userLocations, ElapsedTime elapsedTime) {
        Run run = new Run(ratingBarNumStars, note, userLocations, elapsedTime);
        new SaveRunAsyncTask(runLocalStorage, (_aVoid) -> onSaveComplete()).execute(run);
    }
}
