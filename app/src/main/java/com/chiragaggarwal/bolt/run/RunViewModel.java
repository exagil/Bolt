package com.chiragaggarwal.bolt.run;

import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.chiragaggarwal.bolt.BR;
import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.location.UserLocation;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class RunViewModel extends BaseObservable {
    private static final String COLON = ":";
    private static final String FORMAT_LEADING_ZERO = "%02d";
    private static final String TIME_DEFAULT = "00:00:00";

    private ElapsedTime elapsedTime;
    private boolean isRunning;
    private Resources resources;
    private UserLocation lastKnowsUserLocation;
    private float totalDistanceCoveredInMeters;

    public RunViewModel(Resources resources) {
        this.resources = resources;
    }

    public void setElapsedTime(ElapsedTime elapsedTime) {
        this.elapsedTime = elapsedTime;
        notifyPropertyChanged(BR.elapsedTime);
    }

    @Bindable
    public String getElapsedTime() {
        if (elapsedTime == null) return TIME_DEFAULT;
        return formatWithLeadingZero(elapsedTime.hours()) + COLON + formatWithLeadingZero(elapsedTime.minutes()) + COLON + formatWithLeadingZero(elapsedTime.seconds());
    }

    public void setLocation(UserLocation currentUserLocation) {
        if (isLastLocationKnown()) {
            float distanceCovered = this.lastKnowsUserLocation.distanceTo(currentUserLocation);
            this.totalDistanceCoveredInMeters += distanceCovered;
        }
        this.lastKnowsUserLocation = currentUserLocation;
    }

    private String formatWithLeadingZero(int timeElement) {
        return String.format(FORMAT_LEADING_ZERO, timeElement);
    }

    public void setRunningAsStarted() {
        isRunning = true;
        notifyPropertyChanged(BR.toggleRunButtonText);
    }

    public void setRunningAsStopped() {
        isRunning = false;
        elapsedTime = null;
        notifyPropertyChanged(BR.toggleRunButtonText);
        notifyPropertyChanged(BR.elapsedTime);
    }

    @Bindable
    public String getToggleRunButtonText() {
        return (isRunning) ? resources.getString(R.string.stop_activity) : resources.getString(R.string.start_activity);
    }

    public String getPace() {
        if (lastKnowsUserLocation == null) return "0.0";
        DecimalFormat paceDecimalFormat = new DecimalFormat("#.#");
        paceDecimalFormat.setRoundingMode(RoundingMode.DOWN);
        return paceDecimalFormat.format(lastKnowsUserLocation.speed);
    }

    public String getDistance() {
        if (totalDistanceCoveredInMeters == 0) return "0.00";
        DecimalFormat paceDecimalFormat = new DecimalFormat("#.##");
        paceDecimalFormat.setRoundingMode(RoundingMode.DOWN);
        return paceDecimalFormat.format(totalDistanceCoveredInMeters);
    }

    private boolean isLastLocationKnown() {
        return lastKnowsUserLocation != null;
    }

    public String getNotificationSubText() {
        return "Elapsed Time: " + getElapsedTime() + "\nDistance: " + getDistance();
    }
}
