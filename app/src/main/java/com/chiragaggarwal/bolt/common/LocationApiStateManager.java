package com.chiragaggarwal.bolt.common;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

public class LocationApiStateManager {
    private Context activityContext;

    public LocationApiStateManager(Context activityContext) {
        this.activityContext = activityContext;
    }

    public boolean doesNotHaveLocationPermission() {
        return doesNotHaveCoarseLocationPermission() || doesNotHaveFineLocationPermission();
    }

    private boolean doesNotHaveFineLocationPermission() {
        return ContextCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }

    private boolean doesNotHaveCoarseLocationPermission() {
        return ContextCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }
}
