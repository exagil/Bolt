package com.chiragaggarwal.bolt.run;

import android.content.ContentValues;

import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema;

import io.reactivex.Observable;

public class Run {
    private final int rating;
    private final String note;
    public final UserLocations userLocations;

    public Run(int rating, String note, UserLocations userLocations) {
        this.rating = rating;
        this.note = note;
        this.userLocations = userLocations;
    }

    public Observable<ContentValues> persistable() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BoltDatabaseSchema.RunSchema.NOTE, note);
        contentValues.put(BoltDatabaseSchema.RunSchema.RATING, rating);
        return Observable.just(contentValues);
    }
}
