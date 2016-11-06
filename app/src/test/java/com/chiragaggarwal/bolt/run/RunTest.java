package com.chiragaggarwal.bolt.run;

import com.chiragaggarwal.bolt.location.UserLocation;
import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class RunTest {
    private UserLocations userLocations;

    @Before
    public void setUp() throws Exception {
        userLocations = new UserLocations();
        userLocations.add(new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f));
    }

    @Test
    public void testThatAUserLocationIsNotEqualToNull() {
        Run userLocation = new Run(5, "valid note", userLocations, new ElapsedTime(123456));
        assertFalse(userLocation.equals(null));
    }

    @Test
    public void testThatAUserLocationIsEqualToItself() {
        Run userLocation = new Run(5, "valid note", userLocations, new ElapsedTime(123456));
        assertTrue(userLocation.equals(userLocation));
    }

    @Test
    public void testThatAUserLocationIsEqualToAnotherLocationWithSameStates() {
        Run userLocation = new Run(5, "valid note", userLocations, new ElapsedTime(123456));
        Run anotherUserLocation = new Run(5, "valid note", userLocations, new ElapsedTime(123456));
        assertTrue(userLocation.equals(anotherUserLocation));
    }

    @Test
    public void testThatAUserLocationIsNotEqualToAnotherLocationWithDifferentStates() {
        Run userLocation = new Run(5, "valid note", userLocations, new ElapsedTime(123456));
        Run anotherUserLocation = new Run(0, "another valid note", null, new ElapsedTime(123456));
        assertFalse(userLocation.equals(anotherUserLocation));
    }

    @Test
    public void testThatAUserLocationIsNotEqualToAnythingOtherThanAUserLocation() {
        Run userLocation = new Run(5, "valid note", userLocations, new ElapsedTime(123456));
        assertFalse(userLocation.equals(new Object()));
    }

    @Test
    public void testThatTwoSameLocationsHaveTheSameHashCode() {
        Run userLocation = new Run(5, "valid note", userLocations, new ElapsedTime(123456));
        Run anotherUserLocation = new Run(5, "valid note", userLocations, new ElapsedTime(123456));
        assertEquals(userLocation.hashCode(), anotherUserLocation.hashCode());
    }
}
