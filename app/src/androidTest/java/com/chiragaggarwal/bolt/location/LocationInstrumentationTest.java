package com.chiragaggarwal.bolt.location;

import android.os.Parcel;

import junit.framework.Assert;

import org.junit.Test;

public class LocationInstrumentationTest {
    @Test
    public void testThatLocationCanBeParcelledSuccessfully() {
        Location location = new Location(12.9611d, 77.6472d, true, 16, true, 3.16f);
        Parcel parcel = Parcel.obtain();
        location.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        Location locationObtainedFromParcelable = Location.CREATOR.createFromParcel(parcel);
        Assert.assertEquals(location, locationObtainedFromParcelable);
    }
}
