package com.chiragaggarwal.bolt;

import android.app.ActivityManager;
import android.content.Context;

public class ServiceStateMonitor {
    private Context applicationContext;

    public ServiceStateMonitor(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public boolean isRunning(Class<?> serviceClass) {
        ActivityManager activityManager = (ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(runningServiceInfo.service.getClassName()))
                return true;
        }
        return false;
    }
}
