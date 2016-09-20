package com.chiragaggarwal.bolt;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationApiClient implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final int LOCATION_REQUEST_PRIORITY = 100;
    private static final long FASTEST_LOCATION_UPDATE_INTERVAL_IN_SECONDS = 1;
    private static final float SMALLEST_DISPLACEMENT_IN_METERS = 1.0f;
    public static final int LOCATION_UPDATE_INTERVAL_IN_SECONDS = 2;
    public static final float ACCURACY_THRESHOLD = 20f;
    public static final float SPEED_THRESHOLD = 100f;
    private GoogleApiClient googleApiClient;
    private LocationChangeListener locationChangeListener;

    public LocationApiClient(GoogleApiClient googleApiClient, LocationChangeListener locationChangeListener) {
        this.googleApiClient = googleApiClient;
        this.locationChangeListener = locationChangeListener;
    }

    public void connect() {
        googleApiClient.registerConnectionCallbacks(this);
        googleApiClient.registerConnectionFailedListener(this);
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(android.location.Location nativeLocation) {
        Location location = new Location(
                nativeLocation.getLatitude(),
                nativeLocation.getLongitude(),
                nativeLocation.hasAccuracy(),
                nativeLocation.getAccuracy(),
                nativeLocation.hasSpeed(),
                nativeLocation.getSpeed()
        );
        if (location.isValid())
            locationChangeListener.onFetchAccurateLocation(location);
    }

    private boolean hasAppropriateAccuracy(android.location.Location nativeLocation) {
        return nativeLocation.hasAccuracy() && isNativeLocationAccuracyExpected(nativeLocation);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = new LocationRequest().
                setPriority(LOCATION_REQUEST_PRIORITY).
                setInterval(LOCATION_UPDATE_INTERVAL_IN_SECONDS).
                setFastestInterval(FASTEST_LOCATION_UPDATE_INTERVAL_IN_SECONDS).
                setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).
                setSmallestDisplacement(SMALLEST_DISPLACEMENT_IN_METERS);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private boolean hasAppropriateSpeed(android.location.Location nativeLocation) {
        return nativeLocation.hasSpeed() && nativeLocation.getSpeed() <= SPEED_THRESHOLD;
    }

    private boolean isNativeLocationAccuracyExpected(android.location.Location nativeLocation) {
        return nativeLocation.getAccuracy() <= ACCURACY_THRESHOLD;
    }
}
