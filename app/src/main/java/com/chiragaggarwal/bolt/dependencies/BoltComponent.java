package com.chiragaggarwal.bolt.dependencies;

import com.chiragaggarwal.bolt.location.OneOffLocationUpdateService;
import com.chiragaggarwal.bolt.run.RunActivity;
import com.chiragaggarwal.bolt.run.RunService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ServicesModule.class})
public interface BoltComponent {
    void inject(RunService runService);

    void inject(RunActivity runActivity);

    void inject(OneOffLocationUpdateService oneOffLocationUpdateService);
}
