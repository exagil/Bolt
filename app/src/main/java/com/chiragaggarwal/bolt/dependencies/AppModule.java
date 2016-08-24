package com.chiragaggarwal.bolt.dependencies;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context applicationContext;

    public AppModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides
    @Singleton
    public Context providesApplicationContext() {
        return applicationContext;
    }
}
