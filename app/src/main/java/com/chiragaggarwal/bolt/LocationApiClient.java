package com.chiragaggarwal.bolt;

public class LocationApiClient {
    private LocationChangeListener locationChangeListener;

    public LocationApiClient(LocationChangeListener locationChangeListener) {
        this.locationChangeListener = locationChangeListener;
    }

    public void onLocationChanged(android.location.Location nativeLocation) {
        Location location = new Location(nativeLocation.getLatitude(), nativeLocation.getLongitude());
        locationChangeListener.onLocationChanged(location);
    }
}
