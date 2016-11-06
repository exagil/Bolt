package com.chiragaggarwal.bolt.run;

import android.content.ContentValues;

import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema;

public class Run {
    private final int rating;
    private final String note;
    public final UserLocations userLocations;

    public Run(int rating, String note, UserLocations userLocations) {
        this.rating = rating;
        this.note = note;
        this.userLocations = userLocations;
    }

    public ContentValues persistable() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BoltDatabaseSchema.RunSchema.NOTE, note);
        contentValues.put(BoltDatabaseSchema.RunSchema.RATING, rating);
        return contentValues;
    }

    public static Run fromContentValues(ContentValues contentValues) {
        String note = contentValues.getAsString(BoltDatabaseSchema.RunSchema.NOTE);
        Integer rating = contentValues.getAsInteger(BoltDatabaseSchema.RunSchema.RATING);
        return new Run(rating.intValue(), note, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Run run = (Run) o;

        if (rating != run.rating) return false;
        if (note != null ? !note.equals(run.note) : run.note != null) return false;
        return userLocations != null ? userLocations.equals(run.userLocations) : run.userLocations == null;

    }

    @Override
    public int hashCode() {
        int result = rating;
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (userLocations != null ? userLocations.hashCode() : 0);
        return result;
    }
}
