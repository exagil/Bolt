package com.chiragaggarwal.bolt.run;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.chiragaggarwal.bolt.BoltApplication;
import com.chiragaggarwal.bolt.location.Location;
import com.chiragaggarwal.bolt.location.LocationApiClient;
import com.chiragaggarwal.bolt.location.LocationChangeListener;
import com.chiragaggarwal.bolt.timer.ActivityTimer;
import com.chiragaggarwal.bolt.timer.ElapsedTime;
import com.chiragaggarwal.bolt.timer.TimerUpdateListener;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

public class RunService extends Service implements LocationChangeListener, TimerUpdateListener {
    public static final String ACTION_TIME_TICK = "com.chiragaggarwal.bolt.run.RunService.ACTION_TIME_TICK";
    public static final String ACTION_FETCH_ACCURATE_LOCATION = "com.chiragaggarwal.bolt.run.RunService.ACTION_FETCH_ACCURATE_LOCATION";
    private static final int NOTIFICATION_ID = 1;
    private LocationApiClient locationApiClient;
    private ActivityTimer activityTimer;
    private RunViewModel runViewModel;

    @Inject
    public GoogleApiClient googleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        ((BoltApplication) getApplicationContext()).getBoltComponent().inject(this);
        locationApiClient = new LocationApiClient(googleApiClient, this);
        activityTimer = new ActivityTimer(this);
        runViewModel = new RunViewModel(getResources());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIFICATION_ID, new RunInProgressNotification(this, runViewModel).build());
        locationApiClient.connect();
        activityTimer.start();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        locationApiClient.disconnect();
        activityTimer.stop();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new RunServiceBinder();
    }

    @Override
    public void onFetchAccurateLocation(Location location) {
        Intent timeTickBroadcastIntent = new Intent(RunService.ACTION_FETCH_ACCURATE_LOCATION);
        timeTickBroadcastIntent.putExtra(Location.TAG, location);
        LocalBroadcastManager.getInstance(this).sendBroadcast(timeTickBroadcastIntent);
    }

    @Override
    public void onTimeTick(ElapsedTime elapsedTime) {
        runViewModel.setElapsedTime(elapsedTime);
        updateNotification(runViewModel);
        Intent timeTickBroadcastIntent = new Intent(RunService.ACTION_TIME_TICK);
        timeTickBroadcastIntent.putExtra(ElapsedTime.TAG, elapsedTime);
        LocalBroadcastManager.getInstance(this).sendBroadcast(timeTickBroadcastIntent);
    }

    public class RunServiceBinder extends Binder {
        public RunService getService() {
            return RunService.this;
        }
    }

    private void updateNotification(RunViewModel runViewModel) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, new RunInProgressNotification(this, runViewModel).build());
    }
}
