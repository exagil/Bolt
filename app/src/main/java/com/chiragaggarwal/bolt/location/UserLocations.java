package com.chiragaggarwal.bolt.location;

import java.util.ArrayList;
import java.util.List;

public class UserLocations {
    private List<UserLocation> userLocationList;
    private UserLocation lastVisitedUserLocation;
    private float totalDistanceInKilometers;
    private float currentPaceInKilometersPerHour;

    public UserLocations() {
        this.userLocationList = new ArrayList<>();
    }

    public float totalDistanceInKilometers() {
        return totalDistanceInKilometers;
    }

    public void add(UserLocation userLocation) {
        if (lastVisitedUserLocation != null)
            totalDistanceInKilometers += lastVisitedUserLocation.distanceInKilometersTo(userLocation);
        userLocationList.add(userLocation);
        lastVisitedUserLocation = userLocation;
        currentPaceInKilometersPerHour = userLocation.speedInKilometersPerHour();
    }

    public float currentPaceInKilometersPerHour() {
        return currentPaceInKilometersPerHour;
    }
}
