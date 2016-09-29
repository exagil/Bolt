package com.chiragaggarwal.bolt.location;

import android.os.Parcel;

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
}
