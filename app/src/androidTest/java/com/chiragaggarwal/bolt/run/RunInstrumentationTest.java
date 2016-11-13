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
        Run run = new Run(5, "valid note", new ElapsedTime(123456), createdAt);
        ContentValues runContentValues = run.persistable();
        ContentValues expectedRunContentValue = new ContentValues();
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.RATING, 5);
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.NOTE, "valid note");
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.ELAPSED_TIME_IN_SECONDS, 123456);
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.CREATED_AT, createdAt);
        Assert.assertEquals(expectedRunContentValue, runContentValues);
    }

    @Test
    public void testThatRunCanBeBuiltFromContentValues() {
        long createdAt = System.currentTimeMillis();
        Run run = new Run(5, "valid note", new ElapsedTime(123456), createdAt);
        ContentValues runContentValues = new ContentValues();
        runContentValues.put(BoltDatabaseSchema.RunSchema.RATING, 5);
        runContentValues.put(BoltDatabaseSchema.RunSchema.NOTE, "valid note");
        runContentValues.put(BoltDatabaseSchema.RunSchema.ELAPSED_TIME_IN_SECONDS, 123456);
        runContentValues.put(BoltDatabaseSchema.RunSchema.CREATED_AT, createdAt);
        Assert.assertEquals(run, Run.fromContentValues(runContentValues));
    }
}
