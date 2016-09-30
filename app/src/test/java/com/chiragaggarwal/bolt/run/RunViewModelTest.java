package com.chiragaggarwal.bolt.run;

import android.content.res.Resources;

import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.location.UserLocation;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RunViewModelTest {
    private Resources resources;

    @Before
    public void setup() {
        resources = Mockito.mock(Resources.class);
    }

    @Test
    public void testThatElapsedTimeIsZeroWhenRunHasNotBeenStarted() {
        RunViewModel runViewModel = new RunViewModel(resources);
        Assert.assertEquals("00:00:00", runViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIsOneSecondWhenElapsedTimeIsIncreasedByOne() {
        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.increaseByOneSecond();
        RunViewModel runViewModel = new RunViewModel(resources);
        runViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("00:00:01", runViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIncludesTheNumberOfMinutesWhenElapsedTimeIsInMinutes() {
        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.increaseByOneMinute();
        RunViewModel runViewModel = new RunViewModel(resources);
        runViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("00:01:00", runViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIncludesSecondsCorrectlyWhenMinutesExist() {
        ElapsedTime elapsedTime = new ElapsedTime(63);
        RunViewModel runViewModel = new RunViewModel(resources);
        runViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("00:01:03", runViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIncludesTheNumberOfHoursWhenElapsedTimeIsInHours() {
        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.increaseByOneHour();
        RunViewModel runViewModel = new RunViewModel(resources);
        runViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("01:00:00", runViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIncludesTheCorrectNumberOfHoursWhenTimeIncludesHoursMinutesAndSeconds() {
        ElapsedTime elapsedTime = new ElapsedTime(7383);
        RunViewModel runViewModel = new RunViewModel(resources);
        runViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("02:03:03", runViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIncludesTheCorrectNumberOfSecondsWhenTimeIncludesHoursMinutesAndSeconds() {
        ElapsedTime elapsedTime = new ElapsedTime(82701);
        RunViewModel runViewModel = new RunViewModel(resources);
        runViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("22:58:21", runViewModel.getElapsedTime());
    }

    @Test
    public void testThatToggleRunButtonTextIsStartActivityByDefault() {
        Mockito.when(resources.getString(R.string.start_activity)).thenReturn("START ACTIVITY");
        RunViewModel runViewModel = new RunViewModel(resources);
        Assert.assertEquals("START ACTIVITY", runViewModel.getToggleRunButtonText());
    }

    @Test
    public void testThatToggleRunButtonTextIsStopActivityWhenRunning() {
        Mockito.when(resources.getString(R.string.stop_activity)).thenReturn("STOP ACTIVITY");
        RunViewModel runViewModel = new RunViewModel(resources);
        runViewModel.setRunningAsStarted();
        Assert.assertEquals("STOP ACTIVITY", runViewModel.getToggleRunButtonText());
    }

    @Test
    public void testThatToggleRunButtonTextIsStartActivityWhenNotRunning() {
        Mockito.when(resources.getString(R.string.start_activity)).thenReturn("START ACTIVITY");
        RunViewModel runViewModel = new RunViewModel(resources);
        runViewModel.setRunningAsStarted();
        runViewModel.setRunningAsStopped();
        Assert.assertEquals("START ACTIVITY", runViewModel.getToggleRunButtonText());
    }

    @Test
    public void testThatElapsedTimeIsZeroWhenRunIsStopped() {
        RunViewModel runViewModel = new RunViewModel(resources);
        runViewModel.setRunningAsStarted();
        runViewModel.setElapsedTime(new ElapsedTime(5000));
        runViewModel.setRunningAsStopped();
        Assert.assertEquals("00:00:00", runViewModel.getElapsedTime());
    }

    @Test
    public void testThatPaceIsZeroByDefault() {
        RunViewModel runViewModel = new RunViewModel(resources);
        Assert.assertEquals("0.0", runViewModel.getPace());
    }

    @Test
    public void testThatItOnlyProvidesTheFirstDecimalPlaceOfAnySpeedWhenFormatting() {
        RunViewModel runViewModel = new RunViewModel(resources);
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 19, false, 0.98765f);
        runViewModel.setLocation(userLocation);
        Assert.assertEquals("0.9", runViewModel.getPace());
    }

    @Test
    public void testThatItProvidesADigitAtOnesPlaceWhileFormattingANumberWhichDoesNotHaveADigitAtTensPlace() {
        RunViewModel runViewModel = new RunViewModel(resources);
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 19, false, 1.98765f);
        runViewModel.setLocation(userLocation);
        Assert.assertEquals("1.9", runViewModel.getPace());
    }

    @Test
    public void testThatItProvidesADigitAtTensPlaceWhileFormattingANumberWhichHasADigitAtTensPlace() {
        RunViewModel runViewModel = new RunViewModel(resources);
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 19, false, 11.98765f);
        runViewModel.setLocation(userLocation);
        Assert.assertEquals("11.9", runViewModel.getPace());
    }
}
