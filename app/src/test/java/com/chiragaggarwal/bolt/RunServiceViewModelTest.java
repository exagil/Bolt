package com.chiragaggarwal.bolt;

import android.content.res.Resources;

import com.chiragaggarwal.bolt.timer.ElapsedTime;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RunServiceViewModelTest {
    private Resources resources;

    @Before
    public void setup() {
        resources = Mockito.mock(Resources.class);
    }

    @Test
    public void testThatElapsedTimeIsZeroWhenRunHasNotBeenStarted() {
        RunServiceViewModel runServiceViewModel = new RunServiceViewModel(resources);
        Assert.assertEquals("00:00:00", runServiceViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIsOneSecondWhenElapsedTimeIsIncreasedByOne() {
        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.increaseByOneSecond();
        RunServiceViewModel runServiceViewModel = new RunServiceViewModel(resources);
        runServiceViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("00:00:01", runServiceViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIncludesTheNumberOfMinutesWhenElapsedTimeIsInMinutes() {
        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.increaseByOneMinute();
        RunServiceViewModel runServiceViewModel = new RunServiceViewModel(resources);
        runServiceViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("00:01:00", runServiceViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIncludesSecondsCorrectlyWhenMinutesExist() {
        ElapsedTime elapsedTime = new ElapsedTime(63);
        RunServiceViewModel runServiceViewModel = new RunServiceViewModel(resources);
        runServiceViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("00:01:03", runServiceViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIncludesTheNumberOfHoursWhenElapsedTimeIsInHours() {
        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.increaseByOneHour();
        RunServiceViewModel runServiceViewModel = new RunServiceViewModel(resources);
        runServiceViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("01:00:00", runServiceViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIncludesTheCorrectNumberOfHoursWhenTimeIncludesHoursMinutesAndSeconds() {
        ElapsedTime elapsedTime = new ElapsedTime(7383);
        RunServiceViewModel runServiceViewModel = new RunServiceViewModel(resources);
        runServiceViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("02:03:03", runServiceViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIncludesTheCorrectNumberOfSecondsWhenTimeIncludesHoursMinutesAndSeconds() {
        ElapsedTime elapsedTime = new ElapsedTime(82701);
        RunServiceViewModel runServiceViewModel = new RunServiceViewModel(resources);
        runServiceViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("22:58:21", runServiceViewModel.getElapsedTime());
    }

    @Test
    public void testThatToggleRunButtonTextIsStartActivityByDefault() {
        Mockito.when(resources.getString(R.string.start_activity)).thenReturn("START ACTIVITY");
        RunServiceViewModel runViewModel = new RunServiceViewModel(resources);
        Assert.assertEquals("START ACTIVITY", runViewModel.getToggleRunButtonText());
    }

    @Test
    public void testThatToggleRunButtonTextIsStopActivityWhenRunning() {
        Mockito.when(resources.getString(R.string.stop_activity)).thenReturn("STOP ACTIVITY");
        RunServiceViewModel runViewModel = new RunServiceViewModel(resources);
        runViewModel.setRunningAsStarted();
        Assert.assertEquals("STOP ACTIVITY", runViewModel.getToggleRunButtonText());
    }

    @Test
    public void testThatToggleRunButtonTextIsStartActivityWhenNotRunning() {
        Mockito.when(resources.getString(R.string.start_activity)).thenReturn("START ACTIVITY");
        RunServiceViewModel runViewModel = new RunServiceViewModel(resources);
        runViewModel.setRunningAsStarted();
        runViewModel.setRunningAsStopped();
        Assert.assertEquals("START ACTIVITY", runViewModel.getToggleRunButtonText());
    }

    @Test
    public void testThatElapsedTimeIsZeroWhenRunIsStopped() {
        RunServiceViewModel runViewModel = new RunServiceViewModel(resources);
        runViewModel.setRunningAsStarted();
        runViewModel.setElapsedTime(new ElapsedTime(5000));
        runViewModel.setRunningAsStopped();
        Assert.assertEquals("00:00:00", runViewModel.getElapsedTime());
    }
}
