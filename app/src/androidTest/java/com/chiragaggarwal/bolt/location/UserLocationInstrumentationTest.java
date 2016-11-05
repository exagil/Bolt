package com.chiragaggarwal.bolt.location;

import android.content.ContentValues;
import android.os.Parcel;

import com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema;

import junit.framework.Assert;

import org.junit.Test;

public class UserLocationInstrumentationTest {
    @Test
    public void testThatUserLocationCanBeParcelledSuccessfully() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        Parcel parcel = Parcel.obtain();
        userLocation.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        UserLocation userLocationObtainedFromParcelable = UserLocation.CREATOR.createFromParcel(parcel);
        Assert.assertEquals(userLocation, userLocationObtainedFromParcelable);
    }

    @Test
    public void testThatDistanceToItselfIsZero() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        Assert.assertEquals(0F, userLocation.distanceInKilometersTo(userLocation));
    }

    @Test
    public void testThatItKnowsTheDistanceFromItselfToAnyUserLocationNearby() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        UserLocation anotherUserLocation = new UserLocation(12.9612d, 77.6473d, true, 16, true, 3.16f);
        Assert.assertEquals(.015495687F, userLocation.distanceInKilometersTo(anotherUserLocation));
    }

    @Test
    public void testThatItCanBeConvertedToPersistableContentValuesForm() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 16, true, 3.16f);
        ContentValues userLocationsContentValues = new ContentValues();
        userLocationsContentValues.put(BoltDatabaseSchema.UserLocationsSchema.LATITUDE, 12.9611d);
        userLocationsContentValues.put(BoltDatabaseSchema.UserLocationsSchema.LONGITUDE, 12.9611d);
        userLocationsContentValues.put(BoltDatabaseSchema.UserLocationsSchema.HAS_ACCURACY, true);
        userLocationsContentValues.put(BoltDatabaseSchema.UserLocationsSchema.ACCURACY, 16);
        userLocationsContentValues.put(BoltDatabaseSchema.UserLocationsSchema.HAS_SPEED, true);
        userLocationsContentValues.put(BoltDatabaseSchema.UserLocationsSchema.SPEED, 3.16f);
        Assert.assertEquals(userLocationsContentValues, userLocation.persistable());
    }
}
