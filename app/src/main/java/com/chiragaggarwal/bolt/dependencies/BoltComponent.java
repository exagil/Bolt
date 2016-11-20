package com.chiragaggarwal.bolt.dependencies;

import com.chiragaggarwal.bolt.analytics.FirebaseAnalyticsTracker;
import com.chiragaggarwal.bolt.location.OneOffLocationUpdateService;
import com.chiragaggarwal.bolt.run.complete.RunCompleteActivity;
import com.chiragaggarwal.bolt.run.ongoing.RunActivity;
import com.chiragaggarwal.bolt.run.ongoing.RunService;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ServicesModule.class, LocalStorageModule.class, AnalyticsModule.class})
public interface BoltComponent {
    FirebaseAnalyticsTracker getFirebaseAnalyticsTracker();

    void inject(RunService runService);

    void inject(RunActivity runActivity);

    void inject(OneOffLocationUpdateService oneOffLocationUpdateService);

    void inject(RunCompleteActivity runCompleteActivity);
}
