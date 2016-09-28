package com.chiragaggarwal.bolt.run;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.chiragaggarwal.bolt.BoltApplication;
import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.ServiceStateMonitor;
import com.chiragaggarwal.bolt.databinding.ActivityMainBinding;
import com.chiragaggarwal.bolt.location.Location;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class RunActivity extends AppCompatActivity implements RunView {
    private RunServiceBroadcastReceiver runServiceBroadcastReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private RunViewModel runViewModel;
    private RunPresenter runPresenter;

    @Inject
    public ServiceStateMonitor serviceStateMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);
        ((BoltApplication) getApplication()).getBoltComponent().inject(this);
        initialise(activityMainBinding);
    }

    @Override
    protected void onResume() {
        super.onResume();
        runPresenter.init();
        IntentFilter runServiceIntentFilter = new IntentFilter();
        runServiceIntentFilter.addAction(RunService.ACTION_TIME_TICK);
        localBroadcastManager.registerReceiver(runServiceBroadcastReceiver, runServiceIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        localBroadcastManager.unregisterReceiver(runServiceBroadcastReceiver);
    }

    @Override
    public void startRun() {
        startService(runServiceIntent());
    }

    @Override
    public void stopRun() {
        stopService(runServiceIntent());
    }

    private class RunServiceBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(RunService.ACTION_TIME_TICK)) {
                ElapsedTime elapsedTime = intent.getParcelableExtra(ElapsedTime.TAG);
                runViewModel.setElapsedTime(elapsedTime);
            } else if (action.equals(RunService.ACTION_FETCH_ACCURATE_LOCATION)) {
                Location location = intent.getParcelableExtra(Location.TAG);
                runViewModel.setLocation(location);
            }
        }
    }

    private void initialise(ActivityMainBinding activityMainBinding) {
        runViewModel = new RunViewModel(getResources());
        runPresenter = new RunPresenter(this, runViewModel, serviceStateMonitor);
        activityMainBinding.setRunViewModel(runViewModel);
        activityMainBinding.setRunPresenter(runPresenter);
        runServiceBroadcastReceiver = new RunServiceBroadcastReceiver();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @NonNull
    private Intent runServiceIntent() {
        return new Intent(this, RunService.class);
    }
}
