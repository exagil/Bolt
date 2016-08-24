package com.chiragaggarwal.bolt;

import android.app.Application;

import com.chiragaggarwal.bolt.dependencies.AppModule;
import com.chiragaggarwal.bolt.dependencies.BoltComponent;
import com.chiragaggarwal.bolt.dependencies.DaggerBoltComponent;

public class BoltApplication extends Application {
    private BoltComponent boltComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        boltComponent = DaggerBoltComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public BoltComponent getBoltComponent() {
        return boltComponent;
    }
}
