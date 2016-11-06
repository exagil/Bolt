package com.chiragaggarwal.bolt.location;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.chiragaggarwal.bolt.common.PolylineTransformer;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;

public class UserLocations implements Parcelable, Iterable<UserLocation> {
    public static final String TAG = "com.chiragaggarwal.bolt.location.UserLocations";
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
            totalDistanceInKilometers += lastVisitedUserLocation().distanceInKilometersTo(userLocation);
        }
        userLocationsCollection.add(userLocation);
        currentPaceInKilometersPerHour = userLocation.speedInKilometersPerHour();
    }

    public float currentPaceInKilometersPerHour() {
        return currentPaceInKilometersPerHour;
    }

    public UserLocation latest() {
        return lastVisitedUserLocation();
    }

    public float averagePaceInKilometersPerHour() {
        if (isUserLocationsEmpty())
            return 0F;
        float totalSpeedInKilometersPerHour = 0F;
        for (UserLocation userLocation : userLocationsCollection)
            totalSpeedInKilometersPerHour += userLocation.speedInKilometersPerHour();
        return totalSpeedInKilometersPerHour / userLocationsCollection.size();
    }

    private UserLocation lastVisitedUserLocation() {
        return isUserLocationsEmpty() ? new NullUserLocation() : userLocationsCollection.get(userLocationsCollection.size() - 1);
    }

    private boolean hasUserMoved() {
        return lastVisitedUserLocation().exists();
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
        return userLocationsCollection != null ? userLocationsCollection.equals(that.userLocationsCollection) : that.userLocationsCollection == null;

    }

    @Override
    public int hashCode() {
        int result = userLocationsCollection != null ? userLocationsCollection.hashCode() : 0;
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
        dest.writeTypedList(this.userLocationsCollection);
        dest.writeFloat(this.totalDistanceInKilometers);
        dest.writeFloat(this.currentPaceInKilometersPerHour);
    }

    protected UserLocations(Parcel in) {
        this.userLocationsCollection = in.createTypedArrayList(UserLocation.CREATOR);
        this.totalDistanceInKilometers = in.readFloat();
        this.currentPaceInKilometersPerHour = in.readFloat();
    }

    public static final Creator<UserLocations> CREATOR = new Creator<UserLocations>() {
        @Override
        public UserLocations createFromParcel(Parcel source) {
            return new UserLocations(source);
        }

        @Override
        public UserLocations[] newArray(int size) {
            return new UserLocations[size];
        }
    };

    public List<LatLng> toLatLngs() {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        for (UserLocation userLocation : userLocationsCollection)
            latLngs.add(userLocation.toLatLng());
        return latLngs;
    }

    public boolean hasUserNotMovedAtAll() {
        return isUserLocationsEmpty();
    }

    private boolean isUserLocationsEmpty() {
        return userLocationsCollection.isEmpty();
    }

    public Observable<ContentValues> persistable() {
        return Observable.fromIterable(userLocationsCollection)
                .map((userLocation) -> userLocation.persistable());
    }

    public UserLocation get(int location) {
        return userLocationsCollection.get(location);
    }

    @Override
    public Iterator<UserLocation> iterator() {
        return userLocationsCollection.iterator();
    }

    public String encodedPolyline() {
        return new PolylineTransformer().encode(this);
    }
}
