package com.chiragaggarwal.bolt.timer;

import junit.framework.Assert;

import org.junit.Test;

public class ElapsedTimeTest {
    @Test
    public void testThatItIsNotEqualToNull() {
        ElapsedTime elapsedTime = new ElapsedTime();
        Assert.assertNotSame(null, elapsedTime);
    }

    @Test
    public void testThatItIsEqualToItself() {
        ElapsedTime elapsedTime = new ElapsedTime();
        Assert.assertEquals(elapsedTime, elapsedTime);
    }

    @Test
    public void testThatItIsEqualToAnotherElapsedTimeWithSameAmountOfSecondsElapsed() {
        ElapsedTime elapsedTime = new ElapsedTime();
        ElapsedTime anotherElapsedTime = new ElapsedTime();
        Assert.assertEquals(anotherElapsedTime, elapsedTime);
    }

    @Test
    public void testThatItIsNotEqualToAnotherElapsedTimeWithDifferentAmountOfSecondsElapsed() {
        ElapsedTime elapsedTime = new ElapsedTime();
        ElapsedTime anotherElapsedTime = new ElapsedTime();
        anotherElapsedTime.increaseByOneSecond();
        Assert.assertFalse(elapsedTime.equals(anotherElapsedTime));
    }

    @Test
    public void testThatElapsedTimeIsNotEqualToAnythingWhichIsNotElapsedTime() {
        ElapsedTime elapsedTime = new ElapsedTime();
        Assert.assertFalse(elapsedTime.equals(new Object()));
    }

    @Test
    public void testThatTwoSameElapsedTimesHaveSameHashCodes() {
        ElapsedTime elapsedTime = new ElapsedTime();
        ElapsedTime anotherElapsedTime = new ElapsedTime();
        Assert.assertEquals(anotherElapsedTime.hashCode(), elapsedTime.hashCode());
    }

    @Test
    public void testThatSecondsAreZeroByDefault() {
        ElapsedTime elapsedTime = new ElapsedTime();
        Assert.assertEquals(0, elapsedTime.seconds());
    }

    @Test
    public void testThatElapsedTimeIsOneSecondWhenElapsedTimeIsIncreasedByOne() {
        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.increaseByOneSecond();
        Assert.assertEquals(1, elapsedTime.seconds());
    }

    @Test
    public void testThatMinutesAreZeroByDefault() {
        ElapsedTime elapsedTime = new ElapsedTime();
        Assert.assertEquals(0, elapsedTime.minutes());
    }

    @Test
    public void testThatItKnowsTheNumberOfMinutesWhenElapsedTimeIsInMinutes() {
        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.increaseByOneMinute();
        Assert.assertEquals(1, elapsedTime.minutes());
    }

    @Test
    public void testThatSecondsAreZeroWhenElapsedTimeIsOneMinute() {
        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.increaseByOneMinute();
        Assert.assertEquals(0, elapsedTime.seconds());
    }

    @Test
    public void testThatItKnowsSecondsCorrectlyWhenMinutesExist() {
        ElapsedTime elapsedTime = new ElapsedTime(63);
        Assert.assertEquals(3, elapsedTime.seconds());
        Assert.assertEquals(1, elapsedTime.minutes());
    }

    @Test
    public void testThatHoursAreZeroByDefault() {
        ElapsedTime elapsedTime = new ElapsedTime();
        Assert.assertEquals(0, elapsedTime.hours());
    }

    @Test
    public void testThatItKnowsTheNumberOfHoursWhenElapsedTimeIsInHours() {
        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.increaseByOneHour();
        Assert.assertEquals(1, elapsedTime.hours());
    }

    @Test
    public void testThatElapsedTimeKnowsTheCorrectNumberOfHoursWhenTimeIncludesHoursMinutesAndSeconds() {
        ElapsedTime elapsedTime = new ElapsedTime(7383);
        Assert.assertEquals(2, elapsedTime.hours());
    }

    @Test
    public void testThatElapsedTimeKnowsTheCorrectNumberOfMinutesWhenTimeIncludesHoursMinutesAndSeconds() {
        ElapsedTime elapsedTime = new ElapsedTime(7383);
        Assert.assertEquals(3, elapsedTime.minutes());
    }

    @Test
    public void testThatElapsedTimeKnowsTheCorrectNumberOfSecondsWhenTimeIncludesHoursMinutesAndSeconds() {
        ElapsedTime elapsedTime = new ElapsedTime(82701);
        Assert.assertEquals(22, elapsedTime.hours());
        Assert.assertEquals(58, elapsedTime.minutes());
        Assert.assertEquals(21, elapsedTime.seconds());
    }
}
