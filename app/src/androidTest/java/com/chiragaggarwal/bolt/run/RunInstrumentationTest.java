package com.chiragaggarwal.bolt.run;

import android.content.ContentValues;
import android.support.test.runner.AndroidJUnit4;

import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

@RunWith(AndroidJUnit4.class)
public class RunInstrumentationTest {
    @Test
    public void testThatRunKnowsAboutItsPersistableForm() {
        UserLocations userLocations = null;
        Run run = new Run(5, "valid note", userLocations);
        Observable<ContentValues> runObservable = run.persistable();
        TestObserver<ContentValues> observer = new TestObserver<>();
        runObservable.subscribe(observer);
        ContentValues expectedRunContentValue = new ContentValues();
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.RATING, 5);
        expectedRunContentValue.put(BoltDatabaseSchema.RunSchema.NOTE, "valid note");
        observer.assertResult(expectedRunContentValue);
    }
}
