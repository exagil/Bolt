package com.chiragaggarwal.bolt;

public class RunPresenter {
    private RunView runView;
    private RunServiceViewModel runViewModel;
    private ServiceStateMonitor serviceStateMonitor;

    public RunPresenter(RunView runView, RunServiceViewModel runViewModel, ServiceStateMonitor serviceStateMonitor) {
        this.runView = runView;
        this.runViewModel = runViewModel;
        this.serviceStateMonitor = serviceStateMonitor;
    }

    public void onToggleRunClick() {
        if (serviceStateMonitor.isRunning(RunService.class)) {
            runView.stopRun();
            runViewModel.setRunningAsStopped();
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
