package com.chiragaggarwal.bolt.run.ongoing;

import com.chiragaggarwal.bolt.analytics.FirebaseAnalyticsTracker;
import com.chiragaggarwal.bolt.analytics.RunEvent;
import com.chiragaggarwal.bolt.common.ServiceStateMonitor;
import com.chiragaggarwal.bolt.run.RunViewModel;

public class RunPresenter {
    private RunView runView;
    private RunViewModel runViewModel;
    private ServiceStateMonitor serviceStateMonitor;
    private FirebaseAnalyticsTracker firebaseAnalyticsTracker;

    public RunPresenter(RunView runView, RunViewModel runViewModel, ServiceStateMonitor serviceStateMonitor, FirebaseAnalyticsTracker firebaseAnalyticsTracker) {
        this.runView = runView;
        this.runViewModel = runViewModel;
        this.serviceStateMonitor = serviceStateMonitor;
        this.firebaseAnalyticsTracker = firebaseAnalyticsTracker;
    }

    public void onToggleRunClick() {
        if (serviceStateMonitor.isRunning(RunService.class)) {
            firebaseAnalyticsTracker.track(RunEvent.newStopRunEvent());
            runView.stopRun();
            runView.showRunCompleteScreen();
            runViewModel.setRunningAsStopped();
        } else {
            firebaseAnalyticsTracker.track(RunEvent.newStartRunEvent());
            runView.startRun();
            runViewModel.setRunningAsStarted();
        }
    }

    public void init() {
        if (serviceStateMonitor.isRunning(RunService.class))
            runViewModel.setRunningAsStarted();
        else
            runViewModel.setRunningAsStopped();
    }
}
