package com.chiragaggarwal.bolt;

public class Location {
    private final double latitude;
    private final double longitude;
    private final android.location.Location nativeLocation;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.nativeLocation = buildNativeLocation(latitude, longitude);
    }

    public float distanceTo(Location destination) {
        return nativeLocation.distanceTo(destination.nativeLocation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (Double.compare(location.latitude, latitude) != 0) return false;
        return Double.compare(location.longitude, longitude) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    private android.location.Location buildNativeLocation(double latitude, double longitude) {
        android.location.Location location = new android.location.Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }
}
