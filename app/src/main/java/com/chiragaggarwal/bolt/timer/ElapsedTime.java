package com.chiragaggarwal.bolt.timer;

public class ElapsedTime {
    private int elapsedTimeInSeconds;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof ElapsedTime)) return false;
        ElapsedTime thatElapsedTime = (ElapsedTime) o;
        return this.elapsedTimeInSeconds == thatElapsedTime.elapsedTimeInSeconds;
    }

    public void increaseByOneSecond() {
        this.elapsedTimeInSeconds = 1;
    }

    @Override
    public int hashCode() {
        return elapsedTimeInSeconds;
    }

    public int seconds() {
        return elapsedTimeInSeconds;
    }

    public int minutes() {
        return 0;
    }
}
