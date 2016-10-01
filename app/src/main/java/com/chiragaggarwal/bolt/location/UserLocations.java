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
}
