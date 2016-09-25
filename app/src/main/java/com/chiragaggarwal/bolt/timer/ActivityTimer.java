package com.chiragaggarwal.bolt.timer;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityTimer {
    private static final long DELAY_IN_STARTING = 0L;
    private static final long TICK_INTERVAL = 1000L;
    private Timer timer;
    private TimerUpdateListener timerUpdateListener;
    private ElapsedTime elapsedTime;

    public ActivityTimer(TimerUpdateListener timerUpdateListener) {
        this.timerUpdateListener = timerUpdateListener;
        timer = new Timer();
        elapsedTime = new ElapsedTime();
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timerUpdateListener.onTimeTick(elapsedTime);
                elapsedTime.increaseByOneSecond();
            }
        }, DELAY_IN_STARTING, TICK_INTERVAL);
    }

    public void stop() {
        timer.purge();
        timer.cancel();
        timer = new Timer();
        elapsedTime = new ElapsedTime();
    }
}
