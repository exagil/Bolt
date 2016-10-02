package com.chiragaggarwal.bolt.location;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class UserLocationsTest {
    private UserLocations userLocations;

    @Before
    public void setup() {
        userLocations = new UserLocations();
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        userLocations.add(userLocation);
    }

    @Test
    public void testThatDistanceInKilometersIsZeroWhenUserHasNotVisitedAnyLocation() {
        UserLocations userLocations = new UserLocations();
        assertEquals(0F, userLocations.totalDistanceInKilometers());
    }

    @Test
    public void testThatDistanceInKilometersIsZeroWhenUserHasOnlyTravelledOneLocation() {
        UserLocations userLocations = new UserLocations();
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        userLocations.add(userLocation);
        assertEquals(0F, userLocations.totalDistanceInKilometers());
    }

    @Test
    public void testThatCurrentPaceInKilometersPerHourIsZeroByDefault() {
        UserLocations userLocations = new UserLocations();
        assertEquals(0F, userLocations.currentPaceInKilometersPerHour());
    }

    @Test
    public void testThatItKnowsTheCurrentPaceInKilometersPerHourWhenItHasAUserLocationWithPace() {
        UserLocations userLocations = new UserLocations();
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16F);
        userLocations.add(userLocation);
        assertEquals(11.376F, userLocations.currentPaceInKilometersPerHour());
    }

    @Test
    public void testThatUserLocationsAreNotEqualToNull() {
        assertFalse(userLocations.equals(null));
    }

    @Test
    public void testThatUserLocationsAreEqualToThemselves() {
        assertTrue(userLocations.equals(userLocations));
    }

    @Test
    public void testThatUserLocationsAreEqualToAnotherUserLocationsWithSameStates() {
        UserLocations anotherUserLocations = new UserLocations();
        UserLocation anotherUserLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        anotherUserLocations.add(anotherUserLocation);
        assertTrue(this.userLocations.equals(anotherUserLocations));
    }

    @Test
    public void testThatUserLocationsAreNotEqualToAnotherUserLocationsWithDifferentStates() {
        UserLocations anotherUserLocations = new UserLocations();
        assertFalse(userLocations.equals(anotherUserLocations));
    }

    @Test
    public void testThatUserLocationsAreNotEqualToAnythingOtherThanUserLocations() {
        assertFalse(userLocations.equals(new Object()));
    }

    @Test
    public void testThatTwoSameLocationsHaveTheSameHashCode() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        UserLocations anotherUserLocations = new UserLocations();
        anotherUserLocations.add(userLocation);
        assertEquals(this.userLocations.hashCode(), anotherUserLocations.hashCode());
    }
}
