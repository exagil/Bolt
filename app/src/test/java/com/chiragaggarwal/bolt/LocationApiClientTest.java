package com.chiragaggarwal.bolt;


import com.google.android.gms.common.api.GoogleApiClient;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocationApiClientTest {
    @Test
    public void testThatLocationIsProvidedIfAccurateAndIsBelowThreshold() {
        LocationChangeListener locationChangeListener = mock(LocationChangeListener.class);
        GoogleApiClient googleApiClient = mock(GoogleApiClient.class);
        LocationApiClient locationApiClient = new LocationApiClient(googleApiClient, locationChangeListener);
        Location location = new Location(12.9611d, 77.6472d);
        android.location.Location nativeLocation = mock(android.location.Location.class);
        when(nativeLocation.getLatitude()).thenReturn(12.9611d);
        when(nativeLocation.getLongitude()).thenReturn(77.6472d);
        when(nativeLocation.hasAccuracy()).thenReturn(true);
        when(nativeLocation.getAccuracy()).thenReturn(30f);
        locationApiClient.onLocationChanged(nativeLocation);
        verify(locationChangeListener, never()).onFetchAccurateLocation(location);
    }

    @Test
    public void testThatLocationIsProvidedIfAccurate() {
        LocationChangeListener locationChangeListener = mock(LocationChangeListener.class);
        GoogleApiClient googleApiClient = mock(GoogleApiClient.class);
        LocationApiClient locationApiClient = new LocationApiClient(googleApiClient, locationChangeListener);
        Location location = new Location(12.9611d, 77.6472d);
        android.location.Location nativeLocation = mock(android.location.Location.class);
        when(nativeLocation.getLatitude()).thenReturn(12.9611d);
        when(nativeLocation.getLongitude()).thenReturn(77.6472d);
        when(nativeLocation.hasAccuracy()).thenReturn(true);
        when(nativeLocation.getAccuracy()).thenReturn(10f);
        locationApiClient.onLocationChanged(nativeLocation);
        verify(locationChangeListener).onFetchAccurateLocation(location);
    }

    @Test
    public void testThatLocationIsNotProvidedIfItIsNotAccurate() {
        LocationChangeListener locationChangeListener = mock(LocationChangeListener.class);
        GoogleApiClient googleApiClient = mock(GoogleApiClient.class);
        LocationApiClient locationApiClient = new LocationApiClient(googleApiClient, locationChangeListener);
        Location location = new Location(12.9611d, 77.6472d);
        android.location.Location nativeLocation = mock(android.location.Location.class);
        when(nativeLocation.getLatitude()).thenReturn(12.9611d);
        when(nativeLocation.getLongitude()).thenReturn(77.6472d);
        when(nativeLocation.hasAccuracy()).thenReturn(false);
        locationApiClient.onLocationChanged(nativeLocation);
        verify(locationChangeListener, never()).onFetchAccurateLocation(location);
    }

    @Test
    public void testThatLocationApiClientConnectsSuccessfully() {
        LocationChangeListener locationChangeListener = mock(LocationChangeListener.class);
        GoogleApiClient googleApiClient = mock(GoogleApiClient.class);
        LocationApiClient locationApiClient = new LocationApiClient(googleApiClient, locationChangeListener);
        locationApiClient.connect();
        verify(googleApiClient).registerConnectionCallbacks(locationApiClient);
        verify(googleApiClient).registerConnectionFailedListener(locationApiClient);
        verify(googleApiClient).connect();
    }
}
