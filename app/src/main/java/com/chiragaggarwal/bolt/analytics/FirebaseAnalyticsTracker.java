package com.chiragaggarwal.bolt.analytics;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseAnalyticsTracker {
    private FirebaseAnalytics firebaseAnalytics;

    public FirebaseAnalyticsTracker(FirebaseAnalytics firebaseAnalytics) {
        this.firebaseAnalytics = firebaseAnalytics;
    }

    public void track(Analysable event) {
        firebaseAnalytics.logEvent(event.eventName(), event.serialized());
    }
}
