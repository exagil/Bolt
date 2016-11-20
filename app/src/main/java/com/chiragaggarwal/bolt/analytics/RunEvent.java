package com.chiragaggarwal.bolt.analytics;

import android.os.Bundle;

public class RunEvent implements Analysable {
    private String runStatus;

    public static RunEvent newStartRunEvent() {
        return new RunEvent(RunStatus.START);
    }

    public static RunEvent newStopRunEvent() {
        return new RunEvent(RunStatus.STOP);
    }

    public static RunEvent newCompleteRunEvent() {
        return new RunEvent(RunStatus.COMPLETE);
    }

    private RunEvent(@RunStatus String runStatus) {
        this.runStatus = runStatus;
    }

    @Override
    public String eventName() {
        return runStatus + this.getClass().getSimpleName();
    }

    @Override
    public Bundle serialized() {
        Bundle bundle = new Bundle();
        bundle.putLong(EventKeys.FIRE_TIME, System.currentTimeMillis());
        return bundle;
    }
}
