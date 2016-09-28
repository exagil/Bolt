package com.chiragaggarwal.bolt;

import com.chiragaggarwal.bolt.timer.ElapsedTime;

import junit.framework.Assert;

import org.junit.Test;

public class RunServiceViewModelTest {
    @Test
    public void testThatElapsedTimeIsZeroWhenRunHasNotBeenStarted() {
        RunServiceViewModel runServiceViewModel = new RunServiceViewModel();
        Assert.assertEquals("00:00:00", runServiceViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIsOneSecondWhenElapsedTimeIsIncreasedByOne() {
        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.increaseByOneSecond();
        RunServiceViewModel runServiceViewModel = new RunServiceViewModel();
        runServiceViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("00:00:01", runServiceViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIncludesTheNumberOfMinutesWhenElapsedTimeIsInMinutes() {
        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.increaseByOneMinute();
        RunServiceViewModel runServiceViewModel = new RunServiceViewModel();
        runServiceViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("00:01:00", runServiceViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIncludesSecondsCorrectlyWhenMinutesExist() {
        ElapsedTime elapsedTime = new ElapsedTime(63);
        RunServiceViewModel runServiceViewModel = new RunServiceViewModel();
        runServiceViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("00:01:03", runServiceViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIncludesTheNumberOfHoursWhenElapsedTimeIsInHours() {
        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.increaseByOneHour();
        RunServiceViewModel runServiceViewModel = new RunServiceViewModel();
        runServiceViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("01:00:00", runServiceViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIncludesTheCorrectNumberOfHoursWhenTimeIncludesHoursMinutesAndSeconds() {
        ElapsedTime elapsedTime = new ElapsedTime(7383);
        RunServiceViewModel runServiceViewModel = new RunServiceViewModel();
        runServiceViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("02:03:03", runServiceViewModel.getElapsedTime());
    }

    @Test
    public void testThatElapsedTimeIncludesTheCorrectNumberOfSecondsWhenTimeIncludesHoursMinutesAndSeconds() {
        ElapsedTime elapsedTime = new ElapsedTime(82701);
        RunServiceViewModel runServiceViewModel = new RunServiceViewModel();
        runServiceViewModel.setElapsedTime(elapsedTime);
        Assert.assertEquals("22:58:21", runServiceViewModel.getElapsedTime());
    }
}
