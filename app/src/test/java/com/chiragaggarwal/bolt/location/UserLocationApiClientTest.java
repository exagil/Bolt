package com.chiragaggarwal.bolt.location;


import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserLocationApiClientTest {
    @Test
    public void testThatUserLocationIsNotProvidedIfItDoesNotHaveAccuracyBelowAThresholdOfTwentyEvenIfItHasSpeed() {
        UserLocationChangeListener userLocationChangeListener = mock(UserLocationChangeListener.class);
        GoogleApiClient googleApiClient = mock(GoogleApiClient.class);
        UserLocationApiClient userLocationApiClient = new UserLocationApiClient(googleApiClient, userLocationChangeListener);
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 30f, true, 30f);
        android.location.Location nativeLocation = mock(android.location.Location.class);
        when(nativeLocation.getLatitude()).thenReturn(12.9611d);
        when(nativeLocation.getLongitude()).thenReturn(77.6472d);
        when(nativeLocation.hasAccuracy()).thenReturn(true);
        when(nativeLocation.getAccuracy()).thenReturn(30f);
        when(nativeLocation.hasSpeed()).thenReturn(true);
        when(nativeLocation.getSpeed()).thenReturn(30f);
        userLocationApiClient.onLocationChanged(nativeLocation);
        verify(userLocationChangeListener, never()).onFetchAccurateLocation(userLocation);
    }

    @Test
    public void testThatUserLocationIsProvidedIfItIsBelowAThresholdOfTwentyAndHasSpeed() {
        UserLocationChangeListener userLocationChangeListener = mock(UserLocationChangeListener.class);
        GoogleApiClient googleApiClient = mock(GoogleApiClient.class);
        UserLocationApiClient userLocationApiClient = new UserLocationApiClient(googleApiClient, userLocationChangeListener);
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 10f, true, 30f);
        Location nativeLocation = validLocation();
        userLocationApiClient.onLocationChanged(nativeLocation);
        verify(userLocationChangeListener).onFetchAccurateLocation(userLocation);
    }

    @Test
    public void testThatUserLocationIsNotProvidedIfItDoesNotHaveAccuracy() {
        UserLocationChangeListener userLocationChangeListener = mock(UserLocationChangeListener.class);
        GoogleApiClient googleApiClient = mock(GoogleApiClient.class);
        UserLocationApiClient userLocationApiClient = new UserLocationApiClient(googleApiClient, userLocationChangeListener);
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, false, 0.0f, true, 30f);
        android.location.Location nativeLocation = mock(android.location.Location.class);
        when(nativeLocation.getLatitude()).thenReturn(12.9611d);
        when(nativeLocation.getLongitude()).thenReturn(77.6472d);
        when(nativeLocation.hasAccuracy()).thenReturn(false);
        userLocationApiClient.onLocationChanged(nativeLocation);
        verify(userLocationChangeListener, never()).onFetchAccurateLocation(userLocation);
    }

    @Test
    public void testThatUserLocationApiClientConnectsSuccessfully() {
        UserLocationChangeListener userLocationChangeListener = mock(UserLocationChangeListener.class);
        GoogleApiClient googleApiClient = mock(GoogleApiClient.class);
        UserLocationApiClient userLocationApiClient = new UserLocationApiClient(googleApiClient, userLocationChangeListener);
        userLocationApiClient.connect();
        verify(googleApiClient).registerConnectionCallbacks(userLocationApiClient);
        verify(googleApiClient).registerConnectionFailedListener(userLocationApiClient);
        verify(googleApiClient).connect();
    }

    @Test
    public void testThatItDisconnectsAfterUpdatingLocationWhenRunAsOneOff() {
        UserLocationChangeListener userLocationChangeListener = mock(UserLocationChangeListener.class);
        GoogleApiClient googleApiClient = mock(GoogleApiClient.class);
        UserLocationApiClient userLocationApiClient = new UserLocationApiClient(googleApiClient, userLocationChangeListener);
        userLocationApiClient.connectForOneOffUpdate();
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 10f, true, 30f);
        Location validFetchedLocation = validLocation();
        userLocationApiClient.onLocationChanged(validFetchedLocation);
        Mockito.verify(userLocationChangeListener).onFetchAccurateLocation(userLocation);
        verify(googleApiClient).unregisterConnectionCallbacks(userLocationApiClient);
        verify(googleApiClient).unregisterConnectionFailedListener(userLocationApiClient);
        verify(googleApiClient).disconnect();
    }

    @Test
    public void testThatItDoesNotDisconnectWhenAccurateLocationIsFetchedAndNotRunAsOneOff() {
        UserLocationChangeListener userLocationChangeListener = mock(UserLocationChangeListener.class);
        GoogleApiClient googleApiClient = mock(GoogleApiClient.class);
        UserLocationApiClient userLocationApiClient = new UserLocationApiClient(googleApiClient, userLocationChangeListener);
        userLocationApiClient.connect();
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 10f, true, 30f);
        android.location.Location validFetchedLocation = validLocation();
        userLocationApiClient.onLocationChanged(validFetchedLocation);
        Mockito.verify(userLocationChangeListener).onFetchAccurateLocation(userLocation);
        verify(googleApiClient, never()).unregisterConnectionCallbacks(userLocationApiClient);
        verify(googleApiClient, never()).unregisterConnectionFailedListener(userLocationApiClient);
        verify(googleApiClient, never()).disconnect();
    }

    @Test
    public void testThatUserLocationApiClientDisconnectsSuccessfully() {
        UserLocationChangeListener userLocationChangeListener = mock(UserLocationChangeListener.class);
        GoogleApiClient googleApiClient = mock(GoogleApiClient.class);
        UserLocationApiClient userLocationApiClient = new UserLocationApiClient(googleApiClient, userLocationChangeListener);
        userLocationApiClient.disconnect();
        verify(googleApiClient).unregisterConnectionCallbacks(userLocationApiClient);
        verify(googleApiClient).unregisterConnectionFailedListener(userLocationApiClient);
        verify(googleApiClient).disconnect();
    }

    @Test
    public void testThatUserLocationIsNotProvidedIfItDoesNotHaveSpeed() {
        UserLocationChangeListener userLocationChangeListener = mock(UserLocationChangeListener.class);
        GoogleApiClient googleApiClient = mock(GoogleApiClient.class);
        UserLocationApiClient userLocationApiClient = new UserLocationApiClient(googleApiClient, userLocationChangeListener);
        android.location.Location nativeLocation = mock(android.location.Location.class);
        when(nativeLocation.getLatitude()).thenReturn(12.9611d);
        when(nativeLocation.getLongitude()).thenReturn(77.6472d);
        when(nativeLocation.hasAccuracy()).thenReturn(true);
        when(nativeLocation.getAccuracy()).thenReturn(15.5f);
        when(nativeLocation.hasSpeed()).thenReturn(false);
        when(nativeLocation.getSpeed()).thenReturn(0.0f);
        userLocationApiClient.onLocationChanged(nativeLocation);
        verify(userLocationChangeListener, never()).onFetchAccurateLocation(Matchers.any(UserLocation.class));
    }

    @Test
    public void testThatUserLocationIsNotProvidedWhenHasInappropriateSpeed() {
        UserLocationChangeListener userLocationChangeListener = mock(UserLocationChangeListener.class);
        GoogleApiClient googleApiClient = mock(GoogleApiClient.class);
        UserLocationApiClient userLocationApiClient = new UserLocationApiClient(googleApiClient, userLocationChangeListener);
        android.location.Location nativeLocation = mock(android.location.Location.class);
        when(nativeLocation.getLatitude()).thenReturn(12.9611d);
        when(nativeLocation.getLongitude()).thenReturn(77.6472d);
        when(nativeLocation.hasAccuracy()).thenReturn(true);
        when(nativeLocation.getAccuracy()).thenReturn(15.5f);
        when(nativeLocation.hasSpeed()).thenReturn(true);
        when(nativeLocation.getSpeed()).thenReturn(1000.765f);
        userLocationApiClient.onLocationChanged(nativeLocation);
        verify(userLocationChangeListener, never()).onFetchAccurateLocation(Matchers.any(UserLocation.class));
    }

    @NonNull
    private Location validLocation() {
        Location validFetchedLocation = mock(Location.class);
        when(validFetchedLocation.getLatitude()).thenReturn(12.9611d);
        when(validFetchedLocation.getLongitude()).thenReturn(77.6472d);
        when(validFetchedLocation.hasAccuracy()).thenReturn(true);
        when(validFetchedLocation.getAccuracy()).thenReturn(10f);
        when(validFetchedLocation.hasSpeed()).thenReturn(true);
        when(validFetchedLocation.getSpeed()).thenReturn(30f);
        return validFetchedLocation;
    }
}
