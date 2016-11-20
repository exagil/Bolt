package com.chiragaggarwal.bolt.run;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Run implements Parcelable {
    public static final String TAG = "run";
    private static final String DATE_FORMAT = "EEE, dd/MM/yyyy";
    private static final String DECIMAL_FORMAT_PATTERN = "#.##";
    private static final String SUFFIX_KILOMETERS = " km";

    public ElapsedTime elapsedTimeInSeconds;
    public String polyline;
    public final String note;

    private float totalDistanceInKilometers;
    private final int rating;
    private final long createdAt;

    public Run(int rating, String note, ElapsedTime elapsedTimeInSeconds, long createdAt, String polyline, float totalDistanceInKilometers) {
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
        this.createdAt = createdAt;
        this.note = note;
        this.rating = rating;
        this.polyline = polyline;
        this.totalDistanceInKilometers = totalDistanceInKilometers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Run run = (Run) o;

        if (rating != run.rating) return false;
        if (Float.compare(run.totalDistanceInKilometers, totalDistanceInKilometers) != 0)
            return false;
        if (createdAt != run.createdAt) return false;
        if (elapsedTimeInSeconds != null ? !elapsedTimeInSeconds.equals(run.elapsedTimeInSeconds) : run.elapsedTimeInSeconds != null)
            return false;
        if (polyline != null ? !polyline.equals(run.polyline) : run.polyline != null) return false;
        return note != null ? note.equals(run.note) : run.note == null;

    }

    @Override
    public int hashCode() {
        int result = elapsedTimeInSeconds != null ? elapsedTimeInSeconds.hashCode() : 0;
        result = 31 * result + rating;
        result = 31 * result + (polyline != null ? polyline.hashCode() : 0);
        result = 31 * result + (totalDistanceInKilometers != +0.0f ? Float.floatToIntBits(totalDistanceInKilometers) : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (int) (createdAt ^ (createdAt >>> 32));
        return result;
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
        contentValues.put(BoltDatabaseSchema.RunSchema.POLYLINE, polyline);
        contentValues.put(BoltDatabaseSchema.RunSchema.TOTAL_DISTANCE_IN_KILOMETERS, totalDistanceInKilometers);
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

    public String formattedTotalDistanceInKilometers() {
        DecimalFormat twoDecimalsFormat = new DecimalFormat(DECIMAL_FORMAT_PATTERN, new DecimalFormatSymbols(Locale.US));
        return twoDecimalsFormat.format(totalDistanceInKilometers) + SUFFIX_KILOMETERS;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.elapsedTimeInSeconds, flags);
        dest.writeInt(this.rating);
        dest.writeString(this.polyline);
        dest.writeFloat(this.totalDistanceInKilometers);
        dest.writeString(this.note);
        dest.writeLong(this.createdAt);
    }

    protected Run(Parcel in) {
        this.elapsedTimeInSeconds = in.readParcelable(ElapsedTime.class.getClassLoader());
        this.rating = in.readInt();
        this.polyline = in.readString();
        this.totalDistanceInKilometers = in.readFloat();
        this.note = in.readString();
        this.createdAt = in.readLong();
    }

    public static final Parcelable.Creator<Run> CREATOR = new Parcelable.Creator<Run>() {
        @Override
        public Run createFromParcel(Parcel source) {
            return new Run(source);
        }

        @Override
        public Run[] newArray(int size) {
            return new Run[size];
        }
    };
}
