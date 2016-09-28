package com.chiragaggarwal.bolt.dependencies;

import android.content.Context;

import com.chiragaggarwal.bolt.ServiceStateMonitor;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesModule {
    @Provides
    @Singleton
    public GoogleApiClient providesLocationEnabledGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .build();
    }

    @Provides
    @Singleton
    public ServiceStateMonitor providesServiceStateListener(Context applicationContext) {
        return new ServiceStateMonitor(applicationContext);
    }
}
