package com.chiragaggarwal.bolt.run;

import com.chiragaggarwal.bolt.location.UserLocation;
import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

import junit.framework.Assert;

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

    @Test
    public void testThatRunKnowsItsCorrespondingPolylineWhenItDoesNotHaveUserLocations() {
        Run run = new Run(5, "valid note", new ElapsedTime(123456), System.currentTimeMillis(), "{mbnA_n|xMSS{@S", 10F);
        Assert.assertEquals("{mbnA_n|xMSS{@S", run.polyline());
    }

    @Test
    public void testThatRunKnowsItsCorrespondingPolylineWhenItHasUserLocations() {
        userLocations = new UserLocations();
        userLocations.add(new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f));
        userLocations.add(new UserLocation(12.9612d, 77.6473d, true, 16, true, 1.25F));
        userLocations.add(new UserLocation(12.9615d, 77.6474d, true, 16, true, 0.75F));
        Run run = new Run(5, "valid note", userLocations, new ElapsedTime(123456));
        Assert.assertEquals("{mbnA_n|xMSS{@S", run.polyline());
    }
}
