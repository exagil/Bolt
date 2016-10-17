package com.chiragaggarwal.bolt.location;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.Assert;

import org.junit.Test;

public class NullUserLocationTest {
    @Test
    public void testThatItDoesNotHaveDistanceToAUserLocation() {
        UserLocation userLocation = new UserLocation(1D, 1D, true, 1F, true, 10F);
        NullUserLocation nullUserLocation = new NullUserLocation();
        Assert.assertEquals(0F, nullUserLocation.distanceInKilometersTo(userLocation));
    }

    @Test
    public void testThatItDoesNotHaveSpeedInKmph() {
        NullUserLocation nullUserLocation = new NullUserLocation();
        Assert.assertEquals(0F, nullUserLocation.speedInKilometersPerHour());
    }

    @Test
    public void testThatItDefaultsToZeroZeroCoordinates() {
        NullUserLocation nullUserLocation = new NullUserLocation();
        Assert.assertEquals(new LatLng(0D, 0D), nullUserLocation.toLatLng());
    }

    @Test
    public void testThatItDoesNotExist() {
        NullUserLocation nullUserLocation = new NullUserLocation();
        Assert.assertFalse(nullUserLocation.exists());
    }

    @Test
    public void testThatItIsNotValid() {
        NullUserLocation nullUserLocation = new NullUserLocation();
        Assert.assertFalse(nullUserLocation.isValid());
    }
}
