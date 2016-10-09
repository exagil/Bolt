package com.chiragaggarwal.bolt.location;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.chiragaggarwal.bolt.BoltApplication;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

public class OneOffLocationUpdateService extends Service implements UserLocationChangeListener {
    public static final String ACTION_ONE_OFF_ACCURATE_LOCATION = "com.chiragaggarwal.bolt.location.OneOffLocationUpdateService.ACTION_ONE_OFF_ACCURATE_LOCATION";
    private UserLocationApiClient userLocationApiClient;

    @Inject
    public GoogleApiClient googleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        ((BoltApplication) getApplication()).getBoltComponent().inject(this);
        userLocationApiClient = new UserLocationApiClient(googleApiClient, this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        userLocationApiClient.connectForOneOffUpdate();
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onFetchAccurateLocation(UserLocation userLocation) {
        Intent fetchOneOffAccurateLocationIntent = new Intent(ACTION_ONE_OFF_ACCURATE_LOCATION);
        fetchOneOffAccurateLocationIntent.putExtra(UserLocation.TAG, userLocation);
        LocalBroadcastManager.getInstance(this).sendBroadcast(fetchOneOffAccurateLocationIntent);
        stopSelf();
    }
}
