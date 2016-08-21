package com.chiragaggarwal.bolt;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationService extends Service implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final float DEFAULT_DISTANCE_INTERVAL = 1.0f;
    private static final float MAX_SPEED = 100f;
    private Location lastLocation;
    private GoogleApiClient client;
    private float completeDistance;
    private static final float ACCURACY_THRESHOLD = 20;
    private float averageSpeed;

    @Override
    public void onDestroy() {
        averageSpeed = 0;
        completeDistance = 0;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        client = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        client.connect();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onLocationChanged(Location location) {
        calculateStats(location);
    }

    private void calculateStats(Location location) {
        if (isLocationAccurate(location)) {
            if (isLastLocationPresent()) {
                float distance = lastLocation.distanceTo(location);
                completeDistance += distance;
                if (location.hasSpeed() && location.getSpeed() < MAX_SPEED) {
                    float speed = location.getSpeed();
                    if (averageSpeed == 0f) {
                        averageSpeed = speed * 3.6f;
                    } else {
                        averageSpeed = (((averageSpeed + speed) / 3.6f) / 2f);
                    }
                }
                Log.d("chi6rag", "Total Distance: " + completeDistance);
                Log.d("chi6rag", "Average Speed: " + averageSpeed + "km/hr");
            }
            lastLocation = location;
        }
    }

    private boolean isLastLocationPresent() {
        return lastLocation != null;
    }

    private boolean isLocationAccurate(Location location) {
        return location.getAccuracy() < ACCURACY_THRESHOLD;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(3)
                .setFastestInterval(1).setPriority(100)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setSmallestDisplacement(DEFAULT_DISTANCE_INTERVAL);
        LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}
