package com.chiragaggarwal.bolt.common;

import android.content.pm.PackageManager;

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

    public void handleLocationPermissionResponse(int requestCode, String[] permissions, int[] grantResults) {
        if (hasLocationPermissionNotBeenGranted(grantResults))
            locationAwareBaseView.showPermissionRequiredNotice();
        else
            updateLocation();
    }

    private boolean hasLocationPermissionNotBeenGranted(int[] grantResults) {
        if (grantResults.length == 0) return true;
        for (int grantResult : grantResults)
            if (grantResult == PackageManager.PERMISSION_DENIED) return true;
        return false;
    }
}
