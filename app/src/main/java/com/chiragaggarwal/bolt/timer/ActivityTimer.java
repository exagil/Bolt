package com.chiragaggarwal.bolt.timer;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityTimer {
    private static final long DELAY_IN_STARTING = 0L;
    private static final long TICK_INTERVAL = 500L;
    private Timer timer;
    private TimerUpdateListener timerUpdateListener;

    public ActivityTimer(TimerUpdateListener timerUpdateListener) {
        this.timerUpdateListener = timerUpdateListener;
        timer = new Timer();
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timerUpdateListener.onTimeTick();
            }
        }, DELAY_IN_STARTING, TICK_INTERVAL);
    }
}
