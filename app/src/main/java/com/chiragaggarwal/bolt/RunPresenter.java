package com.chiragaggarwal.bolt;

public class RunPresenter {
    private RunView runView;
    private ServiceStateMonitor serviceStateMonitor;

    public RunPresenter(RunView runView, ServiceStateMonitor serviceStateMonitor) {
        this.runView = runView;
        this.serviceStateMonitor = serviceStateMonitor;
    }

    public void onToggleRunClick() {
        if (serviceStateMonitor.isRunning(RunService.class))
            runView.stopRun();
        else
            runView.startRun();
    }
}
