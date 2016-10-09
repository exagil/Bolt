package com.chiragaggarwal.bolt.common;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;

public class LocationApiStateMonitor {
    private Context activityContext;

    public LocationApiStateMonitor(Context activityContext) {
        this.activityContext = activityContext;
    }

    public boolean doesNotHaveLocationPermission() {
        return doesNotHaveCoarseLocationPermission() || doesNotHaveFineLocationPermission();
    }

    public boolean isGpsNotEnabled() {
        LocationManager locationManager = (LocationManager) activityContext.getSystemService(Context.LOCATION_SERVICE);
        try {
            return !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            return true;
        }
    }

    private boolean doesNotHaveFineLocationPermission() {
        return ContextCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }

    private boolean doesNotHaveCoarseLocationPermission() {
        return ContextCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }
}
