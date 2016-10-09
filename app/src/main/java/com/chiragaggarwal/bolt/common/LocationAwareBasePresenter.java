package com.chiragaggarwal.bolt.common;

public class LocationAwareBasePresenter {
    private LocationAwareBaseView locationAwareBaseView;
    private LocationApiStateMonitor locationApiStateMonitor;

    public LocationAwareBasePresenter(LocationAwareBaseView locationAwareBaseView, LocationApiStateMonitor locationApiStateMonitor) {
        this.locationAwareBaseView = locationAwareBaseView;
        this.locationApiStateMonitor = locationApiStateMonitor;
    }

    public void updateLocation() {
        if (locationApiStateMonitor.doesNotHaveLocationPermission()) {
            locationAwareBaseView.requestLocationPermission();
        } else if (locationApiStateMonitor.isGpsNotEnabled()) {
            locationAwareBaseView.requestToEnableGps();
        } else {
            locationAwareBaseView.fetchLocationOnce();
        }
    }
}
