package com.chiragaggarwal.bolt.run.map;

import com.chiragaggarwal.bolt.location.UserLocation;

public class RunMapPresenter {
    private RunMapView runMapView;

    public RunMapPresenter(RunMapView runMapView) {
        this.runMapView = runMapView;
    }

    public void extendPolyline(UserLocation lastUserLocation, UserLocation userLocation) {
        if (lastUserLocation.exists())
            runMapView.plotPolyline(lastUserLocation.toLatLng(), userLocation.toLatLng());
    }
}
