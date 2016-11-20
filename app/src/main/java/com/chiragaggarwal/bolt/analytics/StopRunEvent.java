package com.chiragaggarwal.bolt.analytics;

import android.os.Bundle;

public class StopRunEvent implements Analysable {
    private final String eventName;

    public StopRunEvent() {
        this.eventName = this.getClass().getSimpleName();
    }

    @Override
    public String eventName() {
        return this.eventName;
    }

    @Override
    public Bundle serialized() {
        Bundle bundle = new Bundle();
        bundle.putLong(EventKeys.FIRE_TIME, System.currentTimeMillis());
        return bundle;
    }
}
