package com.chiragaggarwal.bolt.dependencies;

import android.content.Context;

import com.chiragaggarwal.bolt.analytics.FirebaseAnalyticsTracker;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@Singleton
public class AnalyticsModule {
    @Provides
    @Singleton
    public FirebaseAnalytics providesFirebaseAnalytics(Context context) {
        return FirebaseAnalytics.getInstance(context);
    }

    @Provides
    @Singleton
    public FirebaseAnalyticsTracker providesFirebaseAnalyticsTracker(FirebaseAnalytics firebaseAnalytics) {
        return new FirebaseAnalyticsTracker(firebaseAnalytics);
    }
}
