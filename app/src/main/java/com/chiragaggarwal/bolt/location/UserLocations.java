package com.chiragaggarwal.bolt.location;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class UserLocations implements Parcelable {
    public static final String TAG = "com.chiragaggarwal.bolt.location.UserLocations";
    private UserLocation lastVisitedUserLocation = new NullUserLocation();
    private List<UserLocation> userLocationsCollection;
    private float totalDistanceInKilometers;
    private float currentPaceInKilometersPerHour;

    public UserLocations() {
        this.userLocationsCollection = new ArrayList<>();
    }

    public float totalDistanceInKilometers() {
        return totalDistanceInKilometers;
    }

    public void add(UserLocation userLocation) {
        if (hasUserMoved()) {
            userLocationsCollection.add(userLocation);
            totalDistanceInKilometers += lastVisitedUserLocation.distanceInKilometersTo(userLocation);
        }
        lastVisitedUserLocation = userLocation;
        currentPaceInKilometersPerHour = userLocation.speedInKilometersPerHour();
    }

    public float currentPaceInKilometersPerHour() {
        return currentPaceInKilometersPerHour;
    }

    private boolean hasUserMoved() {
        return lastVisitedUserLocation != null;
    }

    public UserLocation latest() {
        return lastVisitedUserLocation;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.lastVisitedUserLocation, flags);
        dest.writeFloat(this.totalDistanceInKilometers);
        dest.writeFloat(this.currentPaceInKilometersPerHour);
    }

    protected UserLocations(Parcel in) {
        this.lastVisitedUserLocation = in.readParcelable(UserLocation.class.getClassLoader());
        this.totalDistanceInKilometers = in.readFloat();
        this.currentPaceInKilometersPerHour = in.readFloat();
    }

    public static final Parcelable.Creator<UserLocations> CREATOR = new Parcelable.Creator<UserLocations>() {
        @Override
        public UserLocations createFromParcel(Parcel source) {
            return new UserLocations(source);
        }

        @Override
        public UserLocations[] newArray(int size) {
            return new UserLocations[size];
        }
    };

    public float averagePaceInKilometersPerHour() {
        if (userLocationsCollection.isEmpty())
            return 0F;
        float totalSpeedInKilometersPerHour = 0F;
        for (UserLocation userLocation : userLocationsCollection)
            totalSpeedInKilometersPerHour += userLocation.speedInKilometersPerHour();
        return totalSpeedInKilometersPerHour / userLocationsCollection.size();
    }
}
