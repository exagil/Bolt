package com.chiragaggarwal.bolt.location;

import junit.framework.Assert;

import org.junit.Test;

public class UserLocationsTest {
    @Test
    public void testThatDistanceInKilometersIsZeroWhenUserHasNotVisitedAnyLocation() {
        UserLocations userLocations = new UserLocations();
        Assert.assertEquals(0F, userLocations.totalDistanceInKilometers());
    }
}
