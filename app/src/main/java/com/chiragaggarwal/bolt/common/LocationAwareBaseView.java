package com.chiragaggarwal.bolt.common;

public interface LocationAwareBaseView {
    void fetchLocationOnce();

    void requestToEnableGps();

    void requestPermissions();

    void showPermissionRequiredNotice();
}
