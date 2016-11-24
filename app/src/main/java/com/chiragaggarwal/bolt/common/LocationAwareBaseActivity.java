package com.chiragaggarwal.bolt.common;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.chiragaggarwal.bolt.location.OneOffLocationUpdateService;
import com.chiragaggarwal.bolt.location.UserLocation;

public abstract class LocationAwareBaseActivity extends FadeEnabledActivity implements LocationAwareBaseView {
    private static int REQUEST_CODE_LOCATION = 37;
    private OneOffLocationUpdateBroadcastReceiver oneOffLocationUpdateBroadcastReceiver;
    private LocationAwareBasePresenter locationAwareBasePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oneOffLocationUpdateBroadcastReceiver = new OneOffLocationUpdateBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(oneOffLocationUpdateBroadcastReceiver,
                new IntentFilter(OneOffLocationUpdateService.ACTION_ONE_OFF_ACCURATE_LOCATION));
        LocationApiStateMonitor locationApiStateMonitor = new LocationApiStateMonitor(this);
        locationAwareBasePresenter = new LocationAwareBasePresenter(this, locationApiStateMonitor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationAwareBasePresenter.updateLocation(shouldShowLocationPermissionRationale());
    }

    @Override
    public void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_LOCATION
        );
    }

    @Override
    public void showPermissionRequiredNotice() {
        AlertDialogFactory.getPermissionRequiredAlertDialog(this,
                (dialog, which) -> finish(),
                (dialog, which) -> requestPermissions()
        ).show();
    }

    @Override
    public void requestToEnableGps() {
        AlertDialogFactory.getEnableGpsDialog(this,
                (dialog, which) -> LocationAwareBaseActivity.this.finish(),
                (dialog, which) -> launchLocationChangeSettings()
        ).show();
    }

    private void launchLocationChangeSettings() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    @Override
    public void fetchLocationOnce() {
        Intent oneOffLocationUpdateIntent = new Intent(this, OneOffLocationUpdateService.class);
        startService(oneOffLocationUpdateIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_LOCATION)
            locationAwareBasePresenter.handleLocationPermissionResponse(permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(oneOffLocationUpdateBroadcastReceiver);
    }

    public abstract void onOneOffLocationUpdate(UserLocation userLocation);

    private boolean shouldShowLocationPermissionRationale() {
        return shouldShowRationale(Manifest.permission.ACCESS_COARSE_LOCATION) || shouldShowRationale(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private boolean shouldShowRationale(String permissionName) {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, permissionName);
    }

    private class OneOffLocationUpdateBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(OneOffLocationUpdateService.ACTION_ONE_OFF_ACCURATE_LOCATION)) {
                UserLocation userLocation = intent.getParcelableExtra(UserLocation.TAG);
                LocationAwareBaseActivity.this.onOneOffLocationUpdate(userLocation);
            }
        }
    }
}
