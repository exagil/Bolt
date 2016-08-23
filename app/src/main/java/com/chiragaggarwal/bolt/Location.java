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

    private android.location.Location buildNativeLocation(double latitude, double longitude) {
        android.location.Location location = new android.location.Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }
}
