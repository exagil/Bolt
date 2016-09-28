package com.chiragaggarwal.bolt.dependencies;

import com.chiragaggarwal.bolt.RunActivity;
import com.chiragaggarwal.bolt.RunService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ServicesModule.class})
public interface BoltComponent {
    void inject(RunService runService);

    void inject(RunActivity runActivity);
}
