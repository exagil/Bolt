package com.chiragaggarwal.bolt.location;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testThatAveragePaceInKilometersPerHourIsZeroWhenUserHasNotTravelledAnyLocations() {
        Assert.assertEquals(0F, new UserLocations().averagePaceInKilometersPerHour());
    }

    @Test
    public void testThatAveragePaceInKilometersPerHourIsUsersLastTravelledLocationsPaceWhenOnlyOneLocationHasBeenTravelled() {
        Assert.assertEquals(11.376F, userLocations.averagePaceInKilometersPerHour());
    }

    @Test
    public void testThatAveragePaceInKilometersPerHourIsAnAverageOfAllOfUsersTravelledLocationsPaceWhenBunchOfLocationHasBeenTravelled() {
        userLocations.add(new UserLocation(12.9612d, 77.6473d, true, 16, true, 1.25F));
        userLocations.add(new UserLocation(12.9615d, 77.6474d, true, 16, true, 0.75F));
        Assert.assertEquals(6.192F, userLocations.averagePaceInKilometersPerHour());
    }

    @Test
    public void testThatLatLngsCollectionIsEmptyWhenNoUserLocationsArePresent() {
        List<LatLng> expectedLatLngCollection = new ArrayList<>();
        Assert.assertEquals(expectedLatLngCollection, new UserLocations().toLatLngs());
    }

    @Test
    public void testThatItKnowsAboutTheLatLngsCollectionWhenUserLocationsNotEmpty() {
        userLocations.add(new UserLocation(8.96D, 6.643D, true, 16, true, 3.16f));
        List<LatLng> expectedLatLngCollection = new ArrayList<>();
        expectedLatLngCollection.add(new LatLng(12.9611D, 77.6472D));
        expectedLatLngCollection.add(new LatLng(8.96D, 6.643D));
        Assert.assertEquals(expectedLatLngCollection, userLocations.toLatLngs());
    }

    @Test
    public void testThatItKnowsUserHasNotMovedWhenNoUserLocationsExist() {
        Assert.assertTrue(new UserLocations().hasUserNotMovedAtAll());
    }

    @Test
    public void testThatItKnowsThatTheUserHasMovedWhenUserLocationsExist() {
        Assert.assertFalse(userLocations.hasUserNotMovedAtAll());
    }

    @Test
    public void testThatTotalActivityTimeInSecondsIsZeroWhenNoLocationsHaveBeenTravelled() {
        Assert.assertEquals(0F, new UserLocations().totalActivityTimeInSeconds());
    }
}
