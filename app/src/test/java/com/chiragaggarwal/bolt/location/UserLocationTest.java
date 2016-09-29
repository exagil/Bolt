package com.chiragaggarwal.bolt.location;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class UserLocationTest {
    @Test
    public void testThatAUserLocationIsNotEqualToNull() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        assertFalse(userLocation.equals(null));
    }

    @Test
    public void testThatAUserLocationIsEqualToItself() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        assertTrue(userLocation.equals(userLocation));
    }

    @Test
    public void testThatAUserLocationIsEqualToAnotherLocationWithSameStates() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        UserLocation anotherUserLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        assertTrue(userLocation.equals(anotherUserLocation));
    }

    @Test
    public void testThatAUserLocationIsNotEqualToAnotherLocationWithDifferentStates() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        UserLocation anotherUserLocation = new UserLocation(13.9611d, 78.6472d, false, 5, false, 6.13f);
        assertFalse(userLocation.equals(anotherUserLocation));
    }

    @Test
    public void testThatAUserLocationIsNotEqualToAnythingOtherThanAUserLocation() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        assertFalse(userLocation.equals(new Object()));
    }

    @Test
    public void testThatTwoSameLocationsHaveTheSameHashCode() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        UserLocation anotherUserLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        assertEquals(userLocation.hashCode(), anotherUserLocation.hashCode());
    }

    @Test
    public void testThatAUserLocationWithNoAccuracyIsNotValid() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, false, 100, true, 3.16f);
        assertFalse(userLocation.isValid());
    }

    @Test
    public void testThatAUserLocationWhichHasAccuracyOfTwentyIsValid() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 20, true, 100f);
        assertTrue(userLocation.isValid());
    }

    @Test
    public void testThatAUserLocationWhichDoesNotHaveAccuracyOfValidMeasureIsInvalid() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, false, 100, true, 100f);
        assertFalse(userLocation.isValid());
    }

    @Test
    public void testThatAUserLocationWithAccuracyBelowTwentyIsValid() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 19, true, 100f);
        assertTrue(userLocation.isValid());
    }

    @Test
    public void testThatAUserLocationWithoutSpeedIsNotValid() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 19, false, 3.16f);
        assertFalse(userLocation.isValid());
    }

    @Test
    public void testThatAUserLocationWithSpeedMoreThanOneHundredMetersPerSecondIsNotValid() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 19, true, 101f);
        assertFalse(userLocation.isValid());
    }

    @Test
    public void testThatAUserLocationWithSpeedAsOneHundredMetersPerSecondIsValid() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 19, true, 100f);
        assertTrue(userLocation.isValid());
    }

    @Test
    public void testThatLocationWithSpeedLessThanHundredMetersPerSecondAreValid() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 19, true, 90f);
        assertTrue(userLocation.isValid());
    }
}
