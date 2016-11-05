package com.chiragaggarwal.bolt.location;

import android.content.ContentValues;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema;
import com.google.android.gms.maps.model.LatLng;

public class UserLocation implements Parcelable {
    public static final String TAG = "com.chiragaggarwal.bolt.location.UserLocation.TAG";
    private static final float METERS_IN_ONE_KILOMETER = 1000F;
    private static final float M_TO_KMPH_CONVERSION_FACTOR = 3.6F;

    private final double latitude;
    private final double longitude;
    private final boolean hasAccuracy;
    private final float accuracy;
    private final boolean hasSpeed;
    private final float speed;

    public UserLocation(double latitude, double longitude, boolean hasAccuracy, float accuracy, boolean hasSpeed, float speed) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.hasAccuracy = hasAccuracy;
        this.accuracy = accuracy;
        this.hasSpeed = hasSpeed;
        this.speed = speed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserLocation userLocation = (UserLocation) o;

        if (Double.compare(userLocation.latitude, latitude) != 0) return false;
        if (Double.compare(userLocation.longitude, longitude) != 0) return false;
        if (hasAccuracy != userLocation.hasAccuracy) return false;
        if (accuracy != userLocation.accuracy) return false;
        if (hasSpeed != userLocation.hasSpeed) return false;
        return Float.compare(userLocation.speed, speed) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (hasAccuracy ? 1 : 0);
        result = 31 * result + (accuracy != +0.0f ? Float.floatToIntBits(accuracy) : 0);
        result = 31 * result + (hasSpeed ? 1 : 0);
        result = 31 * result + (speed != +0.0f ? Float.floatToIntBits(speed) : 0);
        return result;
    }

    @SuppressWarnings("unused")
    private android.location.Location toNative() {
        Location nativeLocation = new Location("");
        nativeLocation.setLatitude(this.latitude);
        nativeLocation.setLongitude(this.longitude);
        nativeLocation.setSpeed(speed);
        nativeLocation.setAccuracy(accuracy);
        return nativeLocation;
    }

    public boolean isValid() {
        return hasValidAccuracy() && hasValidSpeed();
    }

    private boolean hasValidSpeed() {
        return this.hasSpeed && this.speed <= 100;
    }

    private boolean hasValidAccuracy() {
        return this.hasAccuracy && this.accuracy <= 20;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeByte(this.hasAccuracy ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.accuracy);
        dest.writeByte(this.hasSpeed ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.speed);
    }

    protected UserLocation(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.hasAccuracy = in.readByte() != 0;
        this.accuracy = in.readFloat();
        this.hasSpeed = in.readByte() != 0;
        this.speed = in.readFloat();
    }

    public static final Creator<UserLocation> CREATOR = new Creator<UserLocation>() {
        @Override
        public UserLocation createFromParcel(Parcel source) {
            return new UserLocation(source);
        }

        @Override
        public UserLocation[] newArray(int size) {
            return new UserLocation[size];
        }
    };

    public float distanceInKilometersTo(@NonNull UserLocation userLocation) {
        return this.toNative().distanceTo(userLocation.toNative()) / METERS_IN_ONE_KILOMETER;
    }

    public float speedInKilometersPerHour() {
        return speed * M_TO_KMPH_CONVERSION_FACTOR;
    }

    public LatLng toLatLng() {
        return new LatLng(latitude, longitude);
    }

    public boolean exists() {
        return true;
    }

    public ContentValues persistable() {
        ContentValues userLocationsContentValues = new ContentValues();
        userLocationsContentValues.put(BoltDatabaseSchema.UserLocationsSchema.LATITUDE, 12.9611d);
        userLocationsContentValues.put(BoltDatabaseSchema.UserLocationsSchema.LONGITUDE, 12.9611d);
        userLocationsContentValues.put(BoltDatabaseSchema.UserLocationsSchema.HAS_ACCURACY, true);
        userLocationsContentValues.put(BoltDatabaseSchema.UserLocationsSchema.ACCURACY, 16);
        userLocationsContentValues.put(BoltDatabaseSchema.UserLocationsSchema.HAS_SPEED, true);
        userLocationsContentValues.put(BoltDatabaseSchema.UserLocationsSchema.SPEED, 3.16f);
        return userLocationsContentValues;
    }
}
