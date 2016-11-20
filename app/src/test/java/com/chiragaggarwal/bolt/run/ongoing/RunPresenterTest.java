package com.chiragaggarwal.bolt.run.ongoing;

import com.chiragaggarwal.bolt.analytics.FirebaseAnalyticsTracker;
import com.chiragaggarwal.bolt.common.ServiceStateMonitor;
import com.chiragaggarwal.bolt.run.RunViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RunPresenterTest {
    private FirebaseAnalyticsTracker firebaseAnalyticsTracker;

    @Before
    public void setUp() throws Exception {
        this.firebaseAnalyticsTracker = Mockito.mock(FirebaseAnalyticsTracker.class);
        Mockito.doAnswer(invocation -> null).when(firebaseAnalyticsTracker).track(Mockito.any());
    }

    @Test
    public void testThatRunIsStartedWhenClickedOnToggleRunAndWhenRunServiceIsNotRunningInBackground() {
        RunView runView = Mockito.mock(RunView.class);
        ServiceStateMonitor serviceStateMonitor = Mockito.mock(ServiceStateMonitor.class);
        RunViewModel runViewModel = Mockito.mock(RunViewModel.class);
        Mockito.when(serviceStateMonitor.isRunning(RunService.class)).thenReturn(false);
        RunPresenter runPresenter = new RunPresenter(runView, runViewModel, serviceStateMonitor, firebaseAnalyticsTracker);
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
        RunPresenter runPresenter = new RunPresenter(runView, runViewModel, serviceStateMonitor, firebaseAnalyticsTracker);
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
        RunPresenter runPresenter = new RunPresenter(runView, runViewModel, serviceStateMonitor, firebaseAnalyticsTracker);
        runPresenter.init();
        Mockito.verify(runViewModel).setRunningAsStarted();
    }

    @Test
    public void testThatPresenterInitializesTheRunAsStoppedWhenTheRunIsNotInProgress() {
        RunView runView = Mockito.mock(RunView.class);
        ServiceStateMonitor serviceStateMonitor = Mockito.mock(ServiceStateMonitor.class);
        RunViewModel runViewModel = Mockito.mock(RunViewModel.class);
        Mockito.when(serviceStateMonitor.isRunning(RunService.class)).thenReturn(false);
        RunPresenter runPresenter = new RunPresenter(runView, runViewModel, serviceStateMonitor, firebaseAnalyticsTracker);
        runPresenter.init();
        Mockito.verify(runViewModel).setRunningAsStopped();
    }
}
