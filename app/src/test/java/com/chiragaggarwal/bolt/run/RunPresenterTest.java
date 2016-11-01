package com.chiragaggarwal.bolt.run;

import com.chiragaggarwal.bolt.common.ServiceStateMonitor;

import org.junit.Test;
import org.mockito.Mockito;

public class RunPresenterTest {
    @Test
    public void testThatRunIsStartedWhenClickedOnToggleRunAndWhenRunServiceIsNotRunningInBackground() {
        RunView runView = Mockito.mock(RunView.class);
        ServiceStateMonitor serviceStateMonitor = Mockito.mock(ServiceStateMonitor.class);
        RunViewModel runViewModel = Mockito.mock(RunViewModel.class);
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
        RunViewModel runViewModel = Mockito.mock(RunViewModel.class);
        Mockito.when(serviceStateMonitor.isRunning(RunService.class)).thenReturn(true);
        RunPresenter runPresenter = new RunPresenter(runView, runViewModel, serviceStateMonitor);
        runPresenter.onToggleRunClick();
        Mockito.verify(runView).stopRun();
        Mockito.verify(runViewModel).setRunningAsStopped();
        Mockito.verify(runView).showRunCompleteScreen();
    }

    @Test
    public void testThatPresenterInitializesTheRunAsStartedWhenTheRunIsAlreadyInProgress() {
        RunView runView = Mockito.mock(RunView.class);
        ServiceStateMonitor serviceStateMonitor = Mockito.mock(ServiceStateMonitor.class);
        RunViewModel runViewModel = Mockito.mock(RunViewModel.class);
        Mockito.when(serviceStateMonitor.isRunning(RunService.class)).thenReturn(true);
        RunPresenter runPresenter = new RunPresenter(runView, runViewModel, serviceStateMonitor);
        runPresenter.init();
        Mockito.verify(runViewModel).setRunningAsStarted();
    }

    @Test
    public void testThatPresenterInitializesTheRunAsStoppedWhenTheRunIsNotInProgress() {
        RunView runView = Mockito.mock(RunView.class);
        ServiceStateMonitor serviceStateMonitor = Mockito.mock(ServiceStateMonitor.class);
        RunViewModel runViewModel = Mockito.mock(RunViewModel.class);
        Mockito.when(serviceStateMonitor.isRunning(RunService.class)).thenReturn(false);
        RunPresenter runPresenter = new RunPresenter(runView, runViewModel, serviceStateMonitor);
        runPresenter.init();
        Mockito.verify(runViewModel).setRunningAsStopped();
    }
}
