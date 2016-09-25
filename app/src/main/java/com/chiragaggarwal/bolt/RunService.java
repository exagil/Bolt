package com.chiragaggarwal.bolt;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.chiragaggarwal.bolt.timer.ActivityTimer;
import com.chiragaggarwal.bolt.timer.ElapsedTime;
import com.chiragaggarwal.bolt.timer.TimerUpdateListener;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

public class RunService extends Service implements LocationChangeListener, TimerUpdateListener {
    private LocationApiClient locationApiClient;
    private ActivityTimer activityTimer;

    @Inject
    public GoogleApiClient googleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        ((BoltApplication) getApplicationContext()).getBoltComponent().inject(this);
        locationApiClient = new LocationApiClient(googleApiClient, this);
        activityTimer = new ActivityTimer(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationApiClient.connect();
        activityTimer.start();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
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

    }

    @Override
    public void onTimeTick(ElapsedTime elapsedTime) {
    }

    public class RunServiceBinder extends Binder {
        public RunService getService() {
            return RunService.this;
        }
    }
}
