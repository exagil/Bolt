package com.chiragaggarwal.bolt.run;

import android.content.ContentValues;
import android.support.test.runner.AndroidJUnit4;

import com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RunInstrumentationTest {
    @Test
    public void testThatRunKnowsAboutItsPersistableForm() {
        long createdAt = System.currentTimeMillis();
        Run run = new Run(5, "valid note", new ElapsedTime(123456), createdAt, "{mbnA_n|xMSS{@S", 10F);
        ContentValues runContentValues = run.persistable();
        ContentValues expectedRunContentValue = new ContentValues();
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.RATING, 5);
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.NOTE, "valid note");
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.ELAPSED_TIME_IN_SECONDS, 123456);
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.CREATED_AT, createdAt);
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.POLYLINE, "{mbnA_n|xMSS{@S");
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.TOTAL_DISTANCE_IN_KILOMETERS, 10F);
        Assert.assertEquals(expectedRunContentValue, runContentValues);
    }

    @Test
    public void testThatRunKnowsAboutItsPersistableFormWithCorrectPolyline() {
        long createdAt = System.currentTimeMillis();
        Run run = new Run(5, "valid note", new ElapsedTime(123456), createdAt, "{mbnA_n|xMSS{@S", 0.0345F);
        ContentValues runContentValues = run.persistable();
        ContentValues expectedRunContentValue = new ContentValues();
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.RATING, 5);
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.NOTE, "valid note");
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.ELAPSED_TIME_IN_SECONDS, 123456);
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.CREATED_AT, createdAt);
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.POLYLINE, "{mbnA_n|xMSS{@S");
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.TOTAL_DISTANCE_IN_KILOMETERS, 0.05041329F);
        Assert.assertEquals(expectedRunContentValue, runContentValues);
    }

    @Test
    public void testThatRunCanBeBuiltFromContentValues() {
        long createdAt = System.currentTimeMillis();
        Run run = new Run(5, "valid note", new ElapsedTime(123456), createdAt, "{mbnA_n|xMSS{@S", 10F);
        ContentValues runContentValues = new ContentValues();
        runContentValues.put(BoltDatabaseSchema.RunSchema.RATING, 5);
        runContentValues.put(BoltDatabaseSchema.RunSchema.NOTE, "valid note");
        runContentValues.put(BoltDatabaseSchema.RunSchema.ELAPSED_TIME_IN_SECONDS, 123456);
        runContentValues.put(BoltDatabaseSchema.RunSchema.CREATED_AT, createdAt);
        runContentValues.put(BoltDatabaseSchema.RunSchema.POLYLINE, "{mbnA_n|xMSS{@S");
        runContentValues.put(BoltDatabaseSchema.RunSchema.TOTAL_DISTANCE_IN_KILOMETERS, 10F);
        Assert.assertEquals(run, Run.fromContentValues(runContentValues));
    }
}
