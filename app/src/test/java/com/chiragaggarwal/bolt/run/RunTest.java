package com.chiragaggarwal.bolt.run;

import com.chiragaggarwal.bolt.timer.ElapsedTime;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class RunTest {
    @Test
    public void testThatAUserLocationIsNotEqualToNull() {
        Run userLocation = new Run(5, "valid note", new ElapsedTime(123456), 123456L, "", 0.038641F);
        assertFalse(userLocation.equals(null));
    }

    @Test
    public void testThatAUserLocationIsEqualToItself() {
        Run userLocation = new Run(5, "valid note", new ElapsedTime(123456), 123456L, "{mbnA_n|xMSS{@S", 0.038641F);
        assertTrue(userLocation.equals(userLocation));
    }

    @Test
    public void testThatAUserLocationIsEqualToAnotherLocationWithSameStates() {
        Run userLocation = new Run(5, "valid note", new ElapsedTime(123456), 123456L, "{mbnA_n|xMSS{@S", 0.038641F);
        Run anotherUserLocation = new Run(5, "valid note", new ElapsedTime(123456), 123456L, "{mbnA_n|xMSS{@S", 0.038641F);
        assertTrue(userLocation.equals(anotherUserLocation));
    }

    @Test
    public void testThatAUserLocationIsNotEqualToAnotherLocationWithDifferentStates() {
        Run userLocation = new Run(5, "valid note", new ElapsedTime(1234567), 123456L, "{xMSS{@S", 0.98762F);
        Run anotherUserLocation = new Run(0, "another valid note", new ElapsedTime(123456), 1234567L, "{mbnA_n|xMSS{@S", 0.038641F);
        assertFalse(userLocation.equals(anotherUserLocation));
    }

    @Test
    public void testThatAUserLocationIsNotEqualToAnythingOtherThanAUserLocation() {
        Run userLocation = new Run(5, "valid note", new ElapsedTime(123456), 123456L, "{mbnA_n|xMSS{@S", 0.038641F);
        assertFalse(userLocation.equals(new Object()));
    }

    @Test
    public void testThatTwoSameLocationsHaveTheSameHashCode() {
        Run userLocation = new Run(5, "valid note", new ElapsedTime(123456), 123456L, "{mbnA_n|xMSS{@S", 0.038641F);
        Run anotherUserLocation = new Run(5, "valid note", new ElapsedTime(123456), 123456L, "{mbnA_n|xMSS{@S", 0.038641F);
        assertEquals(userLocation.hashCode(), anotherUserLocation.hashCode());
    }
}
