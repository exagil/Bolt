package com.chiragaggarwal.bolt.location;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class LocationTest {
    @Test
    public void testThatALocationIsNotEqualToNull() {
        Location location = new Location(12.9611d, 77.6472d, true, 16, true, 3.16f);
        assertFalse(location.equals(null));
    }

    @Test
    public void testThatALocationIsEqualToItself() {
        Location location = new Location(12.9611d, 77.6472d, true, 16, true, 3.16f);
        assertTrue(location.equals(location));
    }

    @Test
    public void testThatALocationIsEqualToAnotherLocationWithSameStates() {
        Location location = new Location(12.9611d, 77.6472d, true, 16, true, 3.16f);
        Location anotherLocation = new Location(12.9611d, 77.6472d, true, 16, true, 3.16f);
        assertTrue(location.equals(anotherLocation));
    }

    @Test
    public void testThatALocationIsNotEqualToAnotherLocationWithDifferentStates() {
        Location location = new Location(12.9611d, 77.6472d, true, 16, true, 3.16f);
        Location anotherLocation = new Location(13.9611d, 78.6472d, false, 5, false, 6.13f);
        assertFalse(location.equals(anotherLocation));
    }

    @Test
    public void testThatALocationIsNotEqualToAnythingOtherThanALocation() {
        Location location = new Location(12.9611d, 77.6472d, true, 16, true, 3.16f);
        assertFalse(location.equals(new Object()));
    }

    @Test
    public void testThatTwoSameLocationsHaveTheSameHashCode() {
        Location location = new Location(12.9611d, 77.6472d, true, 16, true, 3.16f);
        Location anotherLocation = new Location(12.9611d, 77.6472d, true, 16, true, 3.16f);
        assertEquals(location.hashCode(), anotherLocation.hashCode());
    }

    @Test
    public void testThatALocationWithNoAccuracyIsNotValid() {
        Location location = new Location(12.9611d, 77.6472d, false, 100, true, 3.16f);
        assertFalse(location.isValid());
    }

    @Test
    public void testThatALocationWhichHasAccuracyOfTwentyIsValid() {
        Location location = new Location(12.9611d, 77.6472d, true, 20, true, 100f);
        assertTrue(location.isValid());
    }

    @Test
    public void testThatALocationWhichDoesNotHaveAccuracyOfValidMeasureIsInvalid() {
        Location location = new Location(12.9611d, 77.6472d, false, 100, true, 100f);
        assertFalse(location.isValid());
    }

    @Test
    public void testThatALocationWithAccuracyBelowTwentyIsValid() {
        Location location = new Location(12.9611d, 77.6472d, true, 19, true, 100f);
        assertTrue(location.isValid());
    }

    @Test
    public void testThatALocationWithoutSpeedIsNotValid() {
        Location location = new Location(12.9611d, 77.6472d, true, 19, false, 3.16f);
        assertFalse(location.isValid());
    }

    @Test
    public void testThatALocationWithSpeedMoreThanOneHundredMetersPerSecondIsNotValid() {
        Location location = new Location(12.9611d, 77.6472d, true, 19, true, 101f);
        assertFalse(location.isValid());
    }

    @Test
    public void testThatALocationWithSpeedAsOneHundredMetersPerSecondIsValid() {
        Location location = new Location(12.9611d, 77.6472d, true, 19, true, 100f);
        assertTrue(location.isValid());
    }

    @Test
    public void testThatLocationWithSpeedLessThanHundredMetersPerSecondAreValid() {
        Location location = new Location(12.9611d, 77.6472d, true, 19, true, 90f);
        assertTrue(location.isValid());
    }
}
