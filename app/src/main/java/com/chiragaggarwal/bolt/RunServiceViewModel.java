package com.chiragaggarwal.bolt;

import android.widget.TextView;

import com.chiragaggarwal.bolt.timer.ElapsedTime;

public class RunServiceViewModel {
    public static final String COLON = ":";
    public static final String FORMAT_LEADING_ZERO = "%02d";
    private TextView textTimer;

    public RunServiceViewModel(TextView textTimer) {
        this.textTimer = textTimer;
    }

    public void setElapsedTime(ElapsedTime elapsedTime) {
        String elapsedTimeToDisplay = formatWithLeadingZero(elapsedTime.hours()) + COLON + formatWithLeadingZero(elapsedTime.minutes()) + COLON + formatWithLeadingZero(elapsedTime.seconds());
        textTimer.setText(elapsedTimeToDisplay);
    }

    public void setLocation(Location location) {

    }

    private String formatWithLeadingZero(int timeElement) {
        return String.format(FORMAT_LEADING_ZERO, timeElement);
    }
}
