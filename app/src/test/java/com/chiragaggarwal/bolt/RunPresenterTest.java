package com.chiragaggarwal.bolt;

import org.junit.Test;
import org.mockito.Mockito;

public class RunPresenterTest {
    @Test
    public void testThatRunIsStartedWhenClickedOnToggleRunAndWhenRunServiceIsNotRunningInBackground() {
        RunView runView = Mockito.mock(RunView.class);
        ServiceStateMonitor serviceStateMonitor = Mockito.mock(ServiceStateMonitor.class);
        Mockito.when(serviceStateMonitor.isRunning(RunService.class)).thenReturn(false);
        RunPresenter runPresenter = new RunPresenter(runView, serviceStateMonitor);
        runPresenter.onToggleRunClick();
        Mockito.verify(runView).startRun();
    }

    @Test
    public void testThatRunIsStoppedWhenClickedOnToggleRunAndRunServiceIsRunningInBackground() {
        RunView runView = Mockito.mock(RunView.class);
        ServiceStateMonitor serviceStateMonitor = Mockito.mock(ServiceStateMonitor.class);
        Mockito.when(serviceStateMonitor.isRunning(RunService.class)).thenReturn(true);
        RunPresenter runPresenter = new RunPresenter(runView, serviceStateMonitor);
        runPresenter.onToggleRunClick();
        Mockito.verify(runView).stopRun();
    }
}
