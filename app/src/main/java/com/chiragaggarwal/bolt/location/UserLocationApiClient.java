package com.chiragaggarwal.bolt.location;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class UserLocationApiClient implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final int LOCATION_REQUEST_PRIORITY = 100;
    private static final long FASTEST_LOCATION_UPDATE_INTERVAL_IN_SECONDS = 1;
    private static final float SMALLEST_DISPLACEMENT_IN_METERS = 1.0f;
    private static final int LOCATION_UPDATE_INTERVAL_IN_SECONDS = 2;
    private GoogleApiClient googleApiClient;
    private UserLocationChangeListener userLocationChangeListener;
    private boolean runAsOneOff = false;

    public UserLocationApiClient(GoogleApiClient googleApiClient, UserLocationChangeListener userLocationChangeListener) {
        this.googleApiClient = googleApiClient;
        this.userLocationChangeListener = userLocationChangeListener;
    }

    public void connect() {
        connectViaGoogleApiClient();
    }

    @Override
    public void onLocationChanged(Location nativeLocation) {
        UserLocation userLocation = new UserLocation(
                nativeLocation.getLatitude(),
                nativeLocation.getLongitude(),
                nativeLocation.hasAccuracy(),
                nativeLocation.getAccuracy(),
                nativeLocation.hasSpeed(),
                nativeLocation.getSpeed()
        );
        if (runAsOneOff) {
            userLocationChangeListener.onFetchAccurateLocation(userLocation);
            disconnectFromGoogleApiClient();
        } else if (userLocation.isValid()) {
            userLocationChangeListener.onFetchAccurateLocation(userLocation);
        }
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = new LocationRequest().
                setPriority(LOCATION_REQUEST_PRIORITY).
                setInterval(LOCATION_UPDATE_INTERVAL_IN_SECONDS).
                setFastestInterval(FASTEST_LOCATION_UPDATE_INTERVAL_IN_SECONDS).
                setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (!runAsOneOff)
            locationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT_IN_METERS);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void disconnect() {
        disconnectFromGoogleApiClient();
    }

    public void connectForOneOffUpdate() {
        runAsOneOff = true;
        connectViaGoogleApiClient();
    }

    private void connectViaGoogleApiClient() {
        googleApiClient.registerConnectionCallbacks(this);
        googleApiClient.registerConnectionFailedListener(this);
        googleApiClient.connect();
    }

    private void disconnectFromGoogleApiClient() {
        googleApiClient.unregisterConnectionCallbacks(this);
        googleApiClient.unregisterConnectionFailedListener(this);
        googleApiClient.disconnect();
    }
}
