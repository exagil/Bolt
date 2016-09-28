package com.chiragaggarwal.bolt;

import org.junit.Test;
import org.mockito.Mockito;

public class RunPresenterTest {
    @Test
    public void testThatRunIsStartedWhenClickedOnToggleRunAndWhenRunServiceIsNotRunningInBackground() {
        RunView runView = Mockito.mock(RunView.class);
        ServiceStateMonitor serviceStateMonitor = Mockito.mock(ServiceStateMonitor.class);
        RunServiceViewModel runViewModel = Mockito.mock(RunServiceViewModel.class);
        Mockito.when(serviceStateMonitor.isRunning(RunService.class)).thenReturn(false);
        RunPresenter runPresenter = new RunPresenter(runView, runViewModel, serviceStateMonitor);
        runPresenter.onToggleRunClick();
        Mockito.verify(runView).startRun();
        Mockito.verify(runViewModel).setRunningAsStarted();
    }

    @Test
    public void testThatRunIsStoppedWhenClickedOnToggleRunAndRunServiceIsRunningInBackground() {
        RunView runView = Mockito.mock(RunView.class);
        ServiceStateMonitor serviceStateMonitor = Mockito.mock(ServiceStateMonitor.class);
        RunServiceViewModel runViewModel = Mockito.mock(RunServiceViewModel.class);
        Mockito.when(serviceStateMonitor.isRunning(RunService.class)).thenReturn(true);
        RunPresenter runPresenter = new RunPresenter(runView, runViewModel, serviceStateMonitor);
        runPresenter.onToggleRunClick();
        Mockito.verify(runView).stopRun();
        Mockito.verify(runViewModel).setRunningAsStopped();
    }

    @Test
    public void testThatPresenterInitializesTheRunAsStartedWhenTheRunIsAlreadyInProgress() {
        RunView runView = Mockito.mock(RunView.class);
        ServiceStateMonitor serviceStateMonitor = Mockito.mock(ServiceStateMonitor.class);
        RunServiceViewModel runViewModel = Mockito.mock(RunServiceViewModel.class);
        Mockito.when(serviceStateMonitor.isRunning(RunService.class)).thenReturn(true);
        RunPresenter runPresenter = new RunPresenter(runView, runViewModel, serviceStateMonitor);
        runPresenter.init();
        Mockito.verify(runViewModel).setRunningAsStarted();
    }
}
