package com.chiragaggarwal.bolt.analytics;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class AppOpenEvent implements Analysable {
    private final String eventName;

    public AppOpenEvent() {
        eventName = FirebaseAnalytics.Event.APP_OPEN;
    }

    @Override
    public String eventName() {
        return eventName;
    }

    @Override
    public Bundle serialized() {
        Bundle bundle = new Bundle();
        bundle.putLong(EventKeys.FIRE_TIME, System.currentTimeMillis());
        return bundle;
    }
}
