package com.chiragaggarwal.bolt;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

public class RunService extends Service implements LocationChangeListener {
    private LocationApiClient locationApiClient;

    @Inject
    public GoogleApiClient googleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        ((BoltApplication) getApplicationContext()).getBoltComponent().inject(this);
        locationApiClient = new LocationApiClient(googleApiClient, this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationApiClient.connect();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new RunServiceBinder();
    }

    @Override
    public void onFetchAccurateLocation(Location location) {
        
    }

    public class RunServiceBinder extends Binder {
        public RunService getService() {
            return RunService.this;
        }
    }
}
