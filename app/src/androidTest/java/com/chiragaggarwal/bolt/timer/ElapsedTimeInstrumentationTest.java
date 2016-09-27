package com.chiragaggarwal.bolt.timer;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ElapsedTimeInstrumentationTest {
    @Test
    public void testThatElapsedTimeCanBeParcelledSuccessfully() {
        ElapsedTime elapsedTime = new ElapsedTime(82631);
        Parcel parcel = Parcel.obtain();
        elapsedTime.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        ElapsedTime elapsedTimeObtainedFromParcelable = ElapsedTime.CREATOR.createFromParcel(parcel);
        Assert.assertEquals(elapsedTime, elapsedTimeObtainedFromParcelable);
    }
}
