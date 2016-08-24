package com.chiragaggarwal.bolt;


import android.content.Context;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocationApiClientTest {
    @Test
    public void testShouldKnowIfALocationChanged() {
        LocationChangeListener locationChangeListener = mock(LocationChangeListener.class);
        Context context = mock(Context.class);
        LocationApiClient locationApiClient = new LocationApiClient(context, locationChangeListener);
        Location location = new Location(12.9611d, 77.6472d);
        android.location.Location nativeLocation = mock(android.location.Location.class);
        when(nativeLocation.getLatitude()).thenReturn(12.9611d);
        when(nativeLocation.getLongitude()).thenReturn(77.6472d);
        locationApiClient.onLocationChanged(nativeLocation);
        verify(locationChangeListener).onLocationChanged(location);
    }
}
