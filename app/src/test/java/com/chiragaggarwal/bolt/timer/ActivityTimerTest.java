package com.chiragaggarwal.bolt.timer;

import com.chiragaggarwal.bolt.timer.ActivityTimer;
import com.chiragaggarwal.bolt.timer.TimerUpdateListener;

import org.junit.Test;
import org.mockito.Mockito;

public class ActivityTimerTest {
    @Test
    public void testThatTimerIsTicksWhenItIsStarted() {
        TimerUpdateListener timerUpdateListener = Mockito.mock(TimerUpdateListener.class);
        ActivityTimer activityTimer = new ActivityTimer(timerUpdateListener);
        activityTimer.start();
        Mockito.verify(timerUpdateListener).onTimeTick();
    }
}
