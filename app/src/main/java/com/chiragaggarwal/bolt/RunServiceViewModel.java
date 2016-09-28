package com.chiragaggarwal.bolt;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.chiragaggarwal.bolt.timer.ElapsedTime;

public class RunServiceViewModel extends BaseObservable {
    private static final String COLON = ":";
    private static final String FORMAT_LEADING_ZERO = "%02d";
    private static final String TIME_DEFAULT = "00:00:00";

    private ElapsedTime elapsedTime;

    public void setElapsedTime(ElapsedTime elapsedTime) {
        this.elapsedTime = elapsedTime;
        notifyPropertyChanged(BR.elapsedTime);
    }

    @Bindable
    public String getElapsedTime() {
        if (elapsedTime == null) return TIME_DEFAULT;
        return formatWithLeadingZero(elapsedTime.hours()) + COLON + formatWithLeadingZero(elapsedTime.minutes()) + COLON + formatWithLeadingZero(elapsedTime.seconds());
    }

    public void setLocation(Location location) {

    }

    private String formatWithLeadingZero(int timeElement) {
        return String.format(FORMAT_LEADING_ZERO, timeElement);
    }
}
