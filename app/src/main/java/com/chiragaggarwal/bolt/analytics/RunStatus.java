package com.chiragaggarwal.bolt.analytics;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({RunStatus.START, RunStatus.STOP})
public @interface RunStatus {
    String STOP = "Stop";
    String START = "Start";
}
