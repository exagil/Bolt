package com.chiragaggarwal.bolt.run;

import android.content.ContentValues;
import android.database.Cursor;

import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

public class Run {
    private final int rating;
    private final String note;
    public final UserLocations userLocations;
    private ElapsedTime elapsedTimeInSeconds;

    public Run(int rating, String note, UserLocations userLocations, ElapsedTime elapsedTimeInSeconds) {
        this.rating = rating;
        this.note = note;
        this.userLocations = userLocations;
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
    }

    public ContentValues persistable() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BoltDatabaseSchema.RunSchema.NOTE, note);
        contentValues.put(BoltDatabaseSchema.RunSchema.RATING, rating);
        contentValues.putAll(elapsedTimeInSeconds.persistable());
        return contentValues;
    }

    public static Run fromContentValues(ContentValues contentValues) {
        String note = contentValues.getAsString(BoltDatabaseSchema.RunSchema.NOTE);
        Integer rating = contentValues.getAsInteger(BoltDatabaseSchema.RunSchema.RATING);
        Integer elapsedTimeInSeconds = contentValues.getAsInteger(BoltDatabaseSchema.RunSchema.ELAPSED_TIME_IN_SECONDS);
        return new Run(rating.intValue(), note, null, new ElapsedTime(elapsedTimeInSeconds));
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

    public ContentValues[] persistableUserLocations(long rowNumber) {
        return userLocations.persistable(rowNumber);
    }

    public static Run fromCursor(Cursor runsCursor) {
        int ratingColumnIndex = runsCursor.getColumnIndex(BoltDatabaseSchema.RunSchema.RATING);
        int noteColumnIndex = runsCursor.getColumnIndex(BoltDatabaseSchema.RunSchema.NOTE);
        int elapsedTimeInSecondsColumnIndex = runsCursor.getColumnIndex(BoltDatabaseSchema.RunSchema.ELAPSED_TIME_IN_SECONDS);

        String note = runsCursor.getString(noteColumnIndex);
        Integer rating = runsCursor.getInt(ratingColumnIndex);
        Integer elapsedTimeInSeconds = runsCursor.getInt(elapsedTimeInSecondsColumnIndex);
        return new Run(rating.intValue(), note, null, new ElapsedTime(elapsedTimeInSeconds));
    }
}
