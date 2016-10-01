package com.chiragaggarwal.bolt.location;

import junit.framework.Assert;

import org.junit.Test;

public class UserLocationsTest {
    @Test
    public void testThatDistanceInKilometersIsZeroWhenUserHasNotVisitedAnyLocation() {
        UserLocations userLocations = new UserLocations();
        Assert.assertEquals(0F, userLocations.totalDistanceInKilometers());
    }

    @Test
    public void testThatDistanceInKilometersIsZeroWhenUserHasOnlyTravelledOneLocation() {
        UserLocations userLocations = new UserLocations();
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        userLocations.add(userLocation);
        Assert.assertEquals(0F, userLocations.totalDistanceInKilometers());
    }
}
