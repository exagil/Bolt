package com.chiragaggarwal.bolt;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class LocationTest {
    @Test
    public void testThatALocationIsNotEqualToNull() {
        Location location = new Location(12.9611d, 77.6472d);
        assertFalse(location.equals(null));
    }

    @Test
    public void testThatALocationIsEqualToItself() {
        Location location = new Location(12.9611d, 77.6472d);
        assertTrue(location.equals(location));
    }

    @Test
    public void testThatALocationIsEqualToAnotherLocationWithSameCoordinates() {
        Location location = new Location(12.9611d, 77.6472d);
        Location anotherLocation = new Location(12.9611d, 77.6472d);
        assertTrue(location.equals(anotherLocation));
    }

    @Test
    public void testThatALocationIsNotEqualToAnotherLocationWithDifferentCoordinates() {
        Location location = new Location(12.9611d, 77.6472d);
        Location anotherLocation = new Location(13.9611d, 78.6472d);
        assertFalse(location.equals(anotherLocation));
    }

    @Test
    public void testThatALocationIsNotEqualToAnythingOtherThanALocation() {
        Location location = new Location(12.9611d, 77.6472d);
        assertFalse(location.equals(new Object()));
    }

    @Test
    public void testThatTwoSameLocationsHaveTheSameHashCode() {
        Location location = new Location(12.9611d, 77.6472d);
        Location anotherLocation = new Location(12.9611d, 77.6472d);
        assertEquals(location.hashCode(), anotherLocation.hashCode());
    }
}
