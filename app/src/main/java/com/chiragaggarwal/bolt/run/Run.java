package com.chiragaggarwal.bolt.run;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Run {
    private static final String DATE_FORMAT = "EEE, dd/MM/yyyy";
    private static final String DECIMAL_FORMAT_PATTERN = "#.##";
    public static final String SUFFIX_KILOMETERS = " km";
    public ElapsedTime elapsedTimeInSeconds;
    private final int rating;
    private String polyline;
    private float totalDistanceInKilometers;
    private final String note;
    public UserLocations userLocations;
    private final long createdAt;

    public Run(int rating, String note, @NonNull UserLocations userLocations, ElapsedTime elapsedTimeInSeconds) {
        this.rating = rating;
        this.note = note;
        this.userLocations = userLocations;
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
        this.createdAt = System.currentTimeMillis();
    }

    @VisibleForTesting
    public Run(int rating, String note, @NonNull UserLocations userLocations, ElapsedTime elapsedTimeInSeconds, long createdAt) {
        this.rating = rating;
        this.note = note;
        this.userLocations = userLocations;
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
        this.createdAt = createdAt;
    }

    public Run(int rating, String note, ElapsedTime elapsedTimeInSeconds, long createdAt, String polyline, float totalDistanceInKilometers) {
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
        this.createdAt = createdAt;
        this.note = note;
        this.rating = rating;
        this.polyline = polyline;
        this.totalDistanceInKilometers = totalDistanceInKilometers;
        this.userLocations = new UserLocations();
    }

    public String formattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date(createdAt);
        return dateFormat.format(date);
    }

    public ContentValues persistable() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BoltDatabaseSchema.RunSchema.NOTE, note);
        contentValues.put(BoltDatabaseSchema.RunSchema.RATING, rating);
        contentValues.putAll(elapsedTimeInSeconds.persistable());
        contentValues.put(BoltDatabaseSchema.RunSchema.CREATED_AT, createdAt);
        contentValues.put(BoltDatabaseSchema.RunSchema.POLYLINE, polyline());
        contentValues.put(BoltDatabaseSchema.RunSchema.TOTAL_DISTANCE_IN_KILOMETERS, totalDistanceInKilometers());
        return contentValues;
    }

    public static Run fromContentValues(ContentValues contentValues) {
        String note = contentValues.getAsString(BoltDatabaseSchema.RunSchema.NOTE);
        Integer rating = contentValues.getAsInteger(BoltDatabaseSchema.RunSchema.RATING);
        Integer elapsedTimeInSeconds = contentValues.getAsInteger(BoltDatabaseSchema.RunSchema.ELAPSED_TIME_IN_SECONDS);
        Long createdAt = contentValues.getAsLong(BoltDatabaseSchema.RunSchema.CREATED_AT);
        String polyline = contentValues.getAsString(BoltDatabaseSchema.RunSchema.POLYLINE);
        Float totalDistanceInKilometers = contentValues.getAsFloat(BoltDatabaseSchema.RunSchema.TOTAL_DISTANCE_IN_KILOMETERS);
        return new Run(rating.intValue(), note, new ElapsedTime(elapsedTimeInSeconds), createdAt, polyline, totalDistanceInKilometers.floatValue());
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
        int createdAtColumnIndex = runsCursor.getColumnIndex(BoltDatabaseSchema.RunSchema.CREATED_AT);
        int polylineColumnIndex = runsCursor.getColumnIndex(BoltDatabaseSchema.RunSchema.POLYLINE);
        int totalDistanceInKilometersColumnIndex = runsCursor.getColumnIndex(BoltDatabaseSchema.RunSchema.TOTAL_DISTANCE_IN_KILOMETERS);

        String note = runsCursor.getString(noteColumnIndex);
        Integer rating = runsCursor.getInt(ratingColumnIndex);
        Integer elapsedTimeInSeconds = runsCursor.getInt(elapsedTimeInSecondsColumnIndex);
        long createdAt = runsCursor.getLong(createdAtColumnIndex);
        String polyline = runsCursor.getString(polylineColumnIndex);
        float totalDistanceInKilometers = runsCursor.getFloat(totalDistanceInKilometersColumnIndex);
        return new Run(rating.intValue(), note, new ElapsedTime(elapsedTimeInSeconds), createdAt, polyline, totalDistanceInKilometers);
    }

    public String polyline() {
        if (userLocations.hasUserNotMovedAtAll())
            return this.polyline;
        else
            return userLocations.encodedPolyline();
    }

    public String formattedTotalDistanceInKilometers() {
        DecimalFormat twoDecimalsFormat = new DecimalFormat(DECIMAL_FORMAT_PATTERN, new DecimalFormatSymbols(Locale.US));
        return twoDecimalsFormat.format(totalDistanceInKilometers()) + SUFFIX_KILOMETERS;
    }

    private float totalDistanceInKilometers() {
        if (userLocations.hasUserNotMovedAtAll())
            return this.totalDistanceInKilometers;
        else
            return userLocations.totalDistanceInKilometers();
    }
}
