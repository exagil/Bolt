package com.chiragaggarwal.bolt.run.persistance;

import android.content.ContentValues;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.chiragaggarwal.bolt.location.UserLocation;
import com.chiragaggarwal.bolt.location.UserLocations;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.observers.TestObserver;

@RunWith(AndroidJUnit4.class)
public class UserLocationsDaoInstrumentationTest {
    private UserLocationsDao userLocationsDao;
    private BoltDatabase boltDatabase;

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getTargetContext();
        boltDatabase = BoltDatabase.getInstance(context);
        boltDatabase.reset();
        userLocationsDao = new UserLocationsDao(boltDatabase);
    }

    @After
    public void tearDown() {
        boltDatabase.reset();
    }

    @Test
    public void testThatItMapsCorrectUserLocationsContentValues() {
        TestObserver<ContentValues> userLocationTestObserver = new TestObserver<>();
        UserLocations userLocationsToInsert = buildUserLocations();
        userLocationsDao.insert(userLocationsToInsert).subscribe(userLocationTestObserver);
        ContentValues firstUserLocationContentValues = userLocationsToInsert.get(0).persistable();
        ContentValues secondUserLocationContentValues = userLocationsToInsert.get(1).persistable();
        ContentValues thirdUserLocationContentValues = userLocationsToInsert.get(2).persistable();
        userLocationTestObserver.assertResult(firstUserLocationContentValues, secondUserLocationContentValues, thirdUserLocationContentValues);
    }

    private UserLocations buildUserLocations() {
        UserLocations userLocations = new UserLocations();
        UserLocation firstUserLocation = new UserLocation(-6.1805294D, 106.8088036D, true, 1, true, 10);
        UserLocation secondUserLocation = new UserLocation(-6.1805228D, 106.8088433D, true, 2, true, 20);
        UserLocation thirdUserLocation = new UserLocation(-6.1805211D, 106.8088532D, true, 3, true, 30);
        userLocations.add(firstUserLocation);
        userLocations.add(secondUserLocation);
        userLocations.add(thirdUserLocation);
        return userLocations;
    }
}
