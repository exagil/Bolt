package com.chiragaggarwal.bolt.run;

import com.chiragaggarwal.bolt.common.ServiceStateMonitor;

public class RunPresenter {
    private RunView runView;
    private RunViewModel runViewModel;
    private ServiceStateMonitor serviceStateMonitor;

    public RunPresenter(RunView runView, RunViewModel runViewModel, ServiceStateMonitor serviceStateMonitor) {
        this.runView = runView;
        this.runViewModel = runViewModel;
        this.serviceStateMonitor = serviceStateMonitor;
    }

    public void onToggleRunClick() {
        if (serviceStateMonitor.isRunning(RunService.class)) {
            runView.stopRun();
            runViewModel.setRunningAsStopped();
            runView.showRunCompleteScreen();
        } else {
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
