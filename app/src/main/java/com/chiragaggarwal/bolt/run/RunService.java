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
import com.chiragaggarwal.bolt.location.UserLocation;
import com.chiragaggarwal.bolt.location.UserLocationApiClient;
import com.chiragaggarwal.bolt.location.UserLocationChangeListener;
import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.timer.ActivityTimer;
import com.chiragaggarwal.bolt.timer.ElapsedTime;
import com.chiragaggarwal.bolt.timer.TimerUpdateListener;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

public class RunService extends Service implements UserLocationChangeListener, TimerUpdateListener {
    public static final String ACTION_TIME_TICK = "com.chiragaggarwal.bolt.run.RunService.ACTION_TIME_TICK";
    public static final String ACTION_FETCH_ACCURATE_LOCATION = "com.chiragaggarwal.bolt.run.RunService.ACTION_FETCH_ACCURATE_LOCATION";
    private static final int NOTIFICATION_ID = 1;
    private UserLocationApiClient userLocationApiClient;
    private ActivityTimer activityTimer;
    private RunViewModel runViewModel;
    private UserLocations userLocations;

    @Inject
    public GoogleApiClient googleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        ((BoltApplication) getApplicationContext()).getBoltComponent().inject(this);
        userLocationApiClient = new UserLocationApiClient(googleApiClient, this);
        activityTimer = new ActivityTimer(this);
        runViewModel = new RunViewModel(getResources());
        userLocations = new UserLocations();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIFICATION_ID, new RunInProgressNotification(this, runViewModel).build());
        userLocationApiClient.connect();
        activityTimer.start();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        userLocationApiClient.disconnect();
        activityTimer.stop();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new RunServiceBinder();
    }

    @Override
    public void onFetchAccurateLocation(UserLocation userLocation) {
        this.userLocations.add(userLocation);
        runViewModel.updateVisitedUserLocations(userLocations);
        updateNotification(runViewModel);
        Intent userLocationsBroadcastIntent = new Intent(RunService.ACTION_FETCH_ACCURATE_LOCATION);
        userLocationsBroadcastIntent.putExtra(UserLocations.TAG, userLocations);
        LocalBroadcastManager.getInstance(this).sendBroadcast(userLocationsBroadcastIntent);
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
