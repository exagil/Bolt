package com.chiragaggarwal.bolt.run;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;

import com.chiragaggarwal.bolt.location.UserLocation;
import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class RunViewModelInstrumentationTest {
    private Resources resources;

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getTargetContext();
        resources = context.getResources();
    }

    @Test
    public void testThatItKnowsHowToFormatDistanceInKilometersCorrectly() {
        RunViewModel runViewModel = new RunViewModel(resources);
        UserLocations userLocations = new UserLocations();
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 19, true, 11.98765f);
        userLocations.add(userLocation);
        runViewModel.updateVisitedUserLocations(userLocations);
        UserLocation secondUserLocation = new UserLocation(12.9612d, 77.6473d, true, 19, true, 11.98765f);
        userLocations.add(secondUserLocation);
        runViewModel.updateVisitedUserLocations(userLocations);
        Assert.assertEquals("0.01", runViewModel.getDistance());
    }

    @Test
    public void testThatItKnowsTheNotificationSubTextWhenElapsedTimeAndDistanceInKilometersIsPresent() {
        RunViewModel runViewModel = new RunViewModel(resources);
        UserLocations userLocations = new UserLocations();
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 19, true, 11.98765f);
        userLocations.add(userLocation);
        runViewModel.updateVisitedUserLocations(userLocations);
        runViewModel.setElapsedTime(new ElapsedTime(123));
        UserLocation secondUserLocation = new UserLocation(12.9612d, 77.6473d, true, 19, true, 11.98765f);
        userLocations.add(secondUserLocation);
        runViewModel.updateVisitedUserLocations(userLocations);
        runViewModel.setElapsedTime(new ElapsedTime(128));
        Assert.assertEquals("Elapsed Time: 00:02:08\nDistance: 0.01", runViewModel.getNotificationSubText());
    }

    @Test
    public void testThatDistanceIsZeroKilometersWhenRunningIsStopped() {
        RunViewModel runViewModel = new RunViewModel(resources);
        runViewModel.setRunningAsStarted();
        UserLocations userLocations = new UserLocations();
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 19, true, 11.98765f);
        userLocations.add(userLocation);
        runViewModel.updateVisitedUserLocations(userLocations);
        UserLocation secondUserLocation = new UserLocation(12.9612d, 77.6473d, true, 19, true, 11.98765f);
        userLocations.add(secondUserLocation);
        runViewModel.updateVisitedUserLocations(userLocations);
        runViewModel.setRunningAsStopped();
        Assert.assertEquals("0.00", runViewModel.getDistance());
    }

    @Test
    public void testThatDistanceIsZeroByDefault() {
        RunViewModel runViewModel = new RunViewModel(resources);
        Assert.assertEquals("0.00", runViewModel.getDistance());
    }

    @Test
    public void testThatVeryMinorDistancesTravelledAreNotTakenIntoConsiderationWhenShowingTotalDistance() {
        UserLocations userLocations = new UserLocations();
        RunViewModel runViewModel = new RunViewModel(resources);
        runViewModel.setRunningAsStarted();
        UserLocation firstUserLocation = new UserLocation(-6.1805294D, 106.8088036D, true, 1, true, 10);
        userLocations.add(firstUserLocation);
        runViewModel.updateVisitedUserLocations(userLocations);
        UserLocation secondUserLocation = new UserLocation(-6.1805228D, 106.8088433D, true, 1, true, 10);
        userLocations.add(secondUserLocation);
        runViewModel.updateVisitedUserLocations(userLocations);
        UserLocation thirdUserLocation = new UserLocation(-6.1805211D, 106.8088532D, true, 1, true, 10);
        userLocations.add(thirdUserLocation);
        runViewModel.updateVisitedUserLocations(userLocations);
        Assert.assertEquals("0.00", runViewModel.getDistance());
    }
}
