package com.chiragaggarwal.bolt.timer;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.atMost;

public class ActivityTimerTest {
    @Test
    public void testThatTimerIsTicksWhenItIsStarted() {
        TimerUpdateListener timerUpdateListener = Mockito.mock(TimerUpdateListener.class);
        ActivityTimer activityTimer = new ActivityTimer(timerUpdateListener);
        ElapsedTime elapsedTime = new ElapsedTime(0);
        activityTimer.start();
        Mockito.verify(timerUpdateListener).onTimeTick(elapsedTime);
    }

    @Test
    public void testThatATimerWhichIsStoppedAfterItIsStartedIsTickedNoMoreThanOnce() throws InterruptedException {
        TimerUpdateListener timerUpdateListener = Mockito.mock(TimerUpdateListener.class);
        ActivityTimer activityTimer = new ActivityTimer(timerUpdateListener);
        activityTimer.start();
        activityTimer.stop();
        Thread.sleep(2500);
        ElapsedTime elapsedTime = new ElapsedTime();
        Mockito.verify(timerUpdateListener, atMost(1)).onTimeTick(elapsedTime);
    }
}
