package com.chiragaggarwal.bolt;

public class Location {
    private final double latitude;
    private final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Location)) return false;
        Location thatLocation = (Location) o;
        return this.latitude == thatLocation.latitude &&
                this.longitude == thatLocation.longitude;
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

    private android.location.Location toNative() {
        android.location.Location nativeLocation = new android.location.Location("");
        nativeLocation.setLatitude(this.latitude);
        nativeLocation.setLongitude(this.longitude);
        return nativeLocation;
    }
}
