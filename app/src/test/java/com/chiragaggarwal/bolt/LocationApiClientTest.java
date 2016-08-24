package com.chiragaggarwal.bolt;


import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LocationApiClientTest {
    @Test
    public void testShouldKnowIfALocationChanged() {
        LocationChangeListener locationChangeListener = mock(LocationChangeListener.class);
        LocationApiClient locationApiClient = new LocationApiClient(locationChangeListener);
        Location location = new Location(12.9611d, 77.6472d);
        android.location.Location nativeLocation = mock(android.location.Location.class);
        Mockito.when(nativeLocation.getLatitude()).thenReturn(12.9611d);
        Mockito.when(nativeLocation.getLongitude()).thenReturn(77.6472d);
        locationApiClient.onLocationChanged(nativeLocation);
        verify(locationChangeListener).onLocationChanged(location);
    }
}
