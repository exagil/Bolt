package com.chiragaggarwal.bolt.location;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

public class NullUserLocation extends UserLocation {

    public NullUserLocation() {
        super(0D, 0D, false, 0F, false, 0F);
    }

    protected NullUserLocation(Parcel in) {
        super(in);
    }

    @Override
    public float distanceInKilometersTo(@NonNull UserLocation userLocation) {
        return 0F;
    }

    @Override
    public float speedInKilometersPerHour() {
        return 0F;
    }

    @Override
    public LatLng toLatLng() {
        return new LatLng(0D, 0D);
    }

    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
