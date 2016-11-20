package com.chiragaggarwal.bolt.run.complete;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.chiragaggarwal.bolt.BoltApplication;
import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.analytics.FirebaseAnalyticsTracker;
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
    private View layoutPhotoCamera;

    @Inject
    public RunLocalStorage runLocalStorage;

    @Inject
    public FirebaseAnalyticsTracker firebaseAnalyticsTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BoltApplication) getApplication()).getBoltComponent().inject(this);
        setContentView(R.layout.activity_run_complete);
        runCompletePresenter = new RunCompletePresenter(this, firebaseAnalyticsTracker);
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
        layoutPhotoCamera.setOnClickListener((view) -> showNextVersionToast());
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.ok, (dialog, which) -> super.onBackPressed())
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setTitle(R.string.notify_dismiss)
                .show();
    }

    private void saveRun() {
        int ratingBarNumStars = ratingBar.getNumStars();
        String note = inputRunNote.getText().toString();
        runCompletePresenter.saveRun(ratingBarNumStars, note, userLocations, elapsedTime);
    }

    private void showNextVersionToast() {
        Toast.makeText(this, R.string.please_wait, Toast.LENGTH_SHORT).show();
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
        layoutPhotoCamera = findViewById(R.id.layout_photo_camera);
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
