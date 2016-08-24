package com.chiragaggarwal.bolt;

public class Location {
    private final double latitude;
    private final double longitude;
    private final boolean hasAccuracy;
    private final int accuracy;
    private final boolean hasSpeed;
    private final float speed;

    public Location(double latitude, double longitude, boolean hasAccuracy, int accuracy, boolean hasSpeed, float speed) {
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

        Location location = (Location) o;

        if (Double.compare(location.latitude, latitude) != 0) return false;
        if (Double.compare(location.longitude, longitude) != 0) return false;
        if (hasAccuracy != location.hasAccuracy) return false;
        if (accuracy != location.accuracy) return false;
        if (hasSpeed != location.hasSpeed) return false;
        return Float.compare(location.speed, speed) == 0;

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
        result = 31 * result + accuracy;
        result = 31 * result + (hasSpeed ? 1 : 0);
        result = 31 * result + (speed != +0.0f ? Float.floatToIntBits(speed) : 0);
        return result;
    }

    private android.location.Location toNative() {
        android.location.Location nativeLocation = new android.location.Location("");
        nativeLocation.setLatitude(this.latitude);
        nativeLocation.setLongitude(this.longitude);
        nativeLocation.setSpeed(speed);
        nativeLocation.setAccuracy(accuracy);
        return nativeLocation;
    }
}
