package com.chiragaggarwal.bolt.common;

public interface LocationAwareBaseView {
    void fetchLocationOnce();

    void requestLocationPermission();

    void requestToEnableGps();

    void showPermissionRequiredNotice();
}
