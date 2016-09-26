package com.chiragaggarwal.bolt.timer;

import android.support.annotation.VisibleForTesting;

public class ElapsedTime implements Cloneable {
    private static final int SECONDS_IN_ONE_MINUTE = 60;
    private static final int SECONDS_IN_ONE_HOUR = 3600;
    private static final int ONE_SECOND = 1;
    private int elapsedTimeInSeconds;

    protected ElapsedTime() {
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

    protected void increaseByOneSecond() {
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

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return new ElapsedTime(this.elapsedTimeInSeconds);
        }
    }
}
