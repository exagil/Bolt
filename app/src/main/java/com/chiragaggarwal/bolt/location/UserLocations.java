package com.chiragaggarwal.bolt.location;

public class UserLocations {
    private UserLocation lastVisitedUserLocation;
    private float totalDistanceInKilometers;
    private float currentPaceInKilometersPerHour;

    public float totalDistanceInKilometers() {
        return totalDistanceInKilometers;
    }

    public void add(UserLocation userLocation) {
        if (hasUserMoved())
            totalDistanceInKilometers += lastVisitedUserLocation.distanceInKilometersTo(userLocation);
        lastVisitedUserLocation = userLocation;
        currentPaceInKilometersPerHour = userLocation.speedInKilometersPerHour();
    }

    public float currentPaceInKilometersPerHour() {
        return currentPaceInKilometersPerHour;
    }

    private boolean hasUserMoved() {
        return lastVisitedUserLocation != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserLocations that = (UserLocations) o;

        if (Float.compare(that.totalDistanceInKilometers, totalDistanceInKilometers) != 0)
            return false;
        if (Float.compare(that.currentPaceInKilometersPerHour, currentPaceInKilometersPerHour) != 0)
            return false;
        return lastVisitedUserLocation != null ? lastVisitedUserLocation.equals(that.lastVisitedUserLocation) : that.lastVisitedUserLocation == null;

    }

    @Override
    public int hashCode() {
        int result = lastVisitedUserLocation != null ? lastVisitedUserLocation.hashCode() : 0;
        result = 31 * result + (totalDistanceInKilometers != +0.0f ? Float.floatToIntBits(totalDistanceInKilometers) : 0);
        result = 31 * result + (currentPaceInKilometersPerHour != +0.0f ? Float.floatToIntBits(currentPaceInKilometersPerHour) : 0);
        return result;
    }
}
