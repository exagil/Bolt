package com.chiragaggarwal.bolt.timer;

import android.support.annotation.VisibleForTesting;

public class ElapsedTime {
    private static final int SECONDS_IN_ONE_MINUTE = 60;
    private static final int SECONDS_IN_ONE_HOUR = 3600;
    private static final int ONE_SECOND = 1;
    private int elapsedTimeInSeconds;

    public ElapsedTime() {
        this.elapsedTimeInSeconds = 0;
    }

    @VisibleForTesting
    ElapsedTime(int elapsedTimeInSeconds) {
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof ElapsedTime)) return false;
        ElapsedTime thatElapsedTime = (ElapsedTime) o;
        return this.elapsedTimeInSeconds == thatElapsedTime.elapsedTimeInSeconds;
    }

    public void increaseByOneSecond() {
        this.elapsedTimeInSeconds += ONE_SECOND;
    }

    @Override
    public int hashCode() {
        return elapsedTimeInSeconds;
    }

    public int seconds() {
        return elapsedTimeInSeconds % SECONDS_IN_ONE_MINUTE;
    }

    public int minutes() {
        return (elapsedTimeInSeconds - hours() * SECONDS_IN_ONE_HOUR) / SECONDS_IN_ONE_MINUTE;
    }

    @VisibleForTesting
    void increaseByOneMinute() {
        elapsedTimeInSeconds += SECONDS_IN_ONE_MINUTE;
    }

    public int hours() {
        return elapsedTimeInSeconds / SECONDS_IN_ONE_HOUR;
    }

    @VisibleForTesting
    void increaseByOneHour() {
        elapsedTimeInSeconds += SECONDS_IN_ONE_HOUR;
    }
}
