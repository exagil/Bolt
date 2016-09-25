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
}
