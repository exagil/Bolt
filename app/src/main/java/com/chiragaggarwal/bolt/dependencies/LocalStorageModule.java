package com.chiragaggarwal.bolt.dependencies;

import android.content.Context;

import com.chiragaggarwal.bolt.run.persistance.RunLocalStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalStorageModule {
    @Provides
    @Singleton
    public RunLocalStorage providesRunLocalStorage(Context context) {
        return new RunLocalStorage(context);
    }
}
