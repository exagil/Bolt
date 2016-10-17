package com.chiragaggarwal.bolt.common;

import android.Manifest;
import android.content.pm.PackageManager;

public class LocationAwareBasePresenter {
    private LocationAwareBaseView locationAwareBaseView;
    private LocationApiStateMonitor locationApiStateMonitor;

    public LocationAwareBasePresenter(LocationAwareBaseView locationAwareBaseView, LocationApiStateMonitor locationApiStateMonitor) {
        this.locationAwareBaseView = locationAwareBaseView;
        this.locationApiStateMonitor = locationApiStateMonitor;
    }

    public void updateLocation(boolean shouldShowLocationPermissionRationale) {
        if (locationApiStateMonitor.doesNotHaveLocationPermission()) {
            requestPermissions(shouldShowLocationPermissionRationale);
        } else if (locationApiStateMonitor.isGpsNotEnabled()) {
            locationAwareBaseView.requestToEnableGps();
        } else {
            locationAwareBaseView.fetchLocationOnce();
        }
    }

    public void handleLocationPermissionResponse(String[] permissions, int[] grantResults) {
        if (hasLocationPermissionNotBeenGranted(permissions, grantResults))
            locationAwareBaseView.showPermissionRequiredNotice();
        else
            updateLocation(false);
    }

    private void requestPermissions(boolean shouldShowLocationPermissionRationale) {
        if (shouldShowLocationPermissionRationale) {
            locationAwareBaseView.showPermissionRequiredNotice();
        } else
            locationAwareBaseView.requestPermissions();
    }

    private boolean hasLocationPermissionNotBeenGranted(String[] permissions, int[] grantResults) {
        if (grantResults.length == 0) return true;
        for (int permissionIndex = 0; permissionIndex < permissions.length; permissionIndex++) {
            String permission = permissions[permissionIndex];
            int grantResult = grantResults[permissionIndex];
            if (isLocationPermission(permission) && isDenied(grantResult))
                return true;
        }
        return false;
    }

    private boolean isLocationPermission(String permission) {
        return permission.equals(Manifest.permission.ACCESS_FINE_LOCATION) || permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    private boolean isDenied(int grantResult) {
        return grantResult == PackageManager.PERMISSION_DENIED;
    }
}
