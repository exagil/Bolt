package com.chiragaggarwal.bolt.location;

import android.content.ContentValues;
import android.os.Parcel;

import junit.framework.Assert;

import org.junit.Test;

public class UserLocationsInstrumentationTest {
    @Test
    public void testThatItKnowsTheTotalDistanceTravelledInKilometersWhenItHasAlreadyVisitedACoupleOfLocations() {
        UserLocations userLocations = new UserLocations();
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 19, true, 11.98765f);
        userLocations.add(userLocation);
        UserLocation secondUserLocation = new UserLocation(12.9612d, 77.6473d, true, 19, true, 11.98765f);
        userLocations.add(secondUserLocation);
        Assert.assertEquals(0.015495687F, userLocations.totalDistanceInKilometers());
    }

    @Test
    public void testThatItKnowsTheTotalDistanceTravelledInKilometersWhenItHasAlreadyVisitedAMultipleLocations() {
        UserLocations userLocations = new UserLocations();
        userLocations.add(new UserLocation(-6.1805294D, 106.8088036D, true, 1, true, 10));
        userLocations.add(new UserLocation(-6.1805228D, 106.8088433D, true, 1, true, 10));
        userLocations.add(new UserLocation(-6.1805211D, 106.8088532D, true, 1, true, 10));
        Assert.assertEquals(0.0055657863F, userLocations.totalDistanceInKilometers());
    }

    @Test
    public void testThatUserLocationCanBeParcelledSuccessfully() {
        UserLocations userLocations = new UserLocations();
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        userLocations.add(userLocation);
        Parcel parcel = Parcel.obtain();
        userLocations.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        UserLocations userLocationsObtainedFromParcelable = UserLocations.CREATOR.createFromParcel(parcel);
        Assert.assertEquals(userLocations, userLocationsObtainedFromParcelable);
    }

    @Test
    public void testThatItKnowsHotToConvertItselfToBePersistable() {
        UserLocations userLocations = new UserLocations();
        UserLocation firstUserLocation = new UserLocation(-6.1805294D, 106.8088036D, true, 1, true, 10);
        UserLocation secondUserLocation = new UserLocation(-6.1805228D, 106.8088433D, true, 2, true, 20);
        UserLocation thirdUserLocation = new UserLocation(-6.1805211D, 106.8088532D, true, 3, true, 30);
        userLocations.add(firstUserLocation);
        userLocations.add(secondUserLocation);
        userLocations.add(thirdUserLocation);

        ContentValues[] expectedUserLocationsContentValues = new ContentValues[3];
        expectedUserLocationsContentValues[0] = firstUserLocation.persistable(1);
        expectedUserLocationsContentValues[1] = secondUserLocation.persistable(1);
        expectedUserLocationsContentValues[2] = thirdUserLocation.persistable(1);
        Assert.assertTrue(expectedUserLocationsContentValues[0].equals(userLocations.persistable(1)[0]));
        Assert.assertTrue(expectedUserLocationsContentValues[1].equals(userLocations.persistable(1)[1]));
        Assert.assertTrue(expectedUserLocationsContentValues[2].equals(userLocations.persistable(1)[2]));
    }
}
