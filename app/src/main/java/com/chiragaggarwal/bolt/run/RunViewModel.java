package com.chiragaggarwal.bolt.run;

import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.chiragaggarwal.bolt.BR;
import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class RunViewModel extends BaseObservable {
    private static final String COLON = ":";
    private static final String FORMAT_LEADING_ZERO = "%02d";
    private static final String TIME_DEFAULT = "00:00:00";
    private static final String DISTANCE_DEFAULT = "0.00";
    private static final String FORMATTED_TOTAL_DISTANCE_ZERO = "0";
    private static final String FORMAT_DISTANCE = "#.##";
    private static final String FORMAT_PACE = "#.#";
    private static final String PACE_DEFAULT = "0.0";

    private ElapsedTime elapsedTime;
    private boolean isRunning;
    private Resources resources;
    private UserLocations userLocations;

    public RunViewModel(Resources resources) {
        this.resources = resources;
    }

    public void setElapsedTime(ElapsedTime elapsedTime) {
        this.elapsedTime = elapsedTime;
        notifyPropertyChanged(BR.elapsedTime);
    }

    @Bindable
    public String getElapsedTime() {
        if (hasTimerNotStartedYet()) return TIME_DEFAULT;
        return formatWithLeadingZero(elapsedTime.hours()) + COLON + formatWithLeadingZero(elapsedTime.minutes()) + COLON + formatWithLeadingZero(elapsedTime.seconds());
    }

    public void setRunningAsStarted() {
        isRunning = true;
        notifyPropertyChanged(BR.toggleRunButtonText);
    }

    public void setRunningAsStopped() {
        isRunning = false;
        elapsedTime = null;
        userLocations = null;
        notifyPropertyChanged(BR.toggleRunButtonText);
        notifyPropertyChanged(BR.elapsedTime);
        notifyPropertyChanged(BR.distance);
        notifyPropertyChanged(BR.pace);
    }

    @Bindable
    public String getToggleRunButtonText() {
        return (isRunning) ? resources.getString(R.string.stop_activity) : resources.getString(R.string.start_activity);
    }

    @Bindable
    public String getPace() {
        if (hasUserNotMovedAtAll()) return PACE_DEFAULT;
        DecimalFormat paceDecimalFormat = new DecimalFormat(FORMAT_PACE);
        paceDecimalFormat.setRoundingMode(RoundingMode.DOWN);
        return paceDecimalFormat.format(userLocations.currentPaceInKilometersPerHour());
    }

    @Bindable
    public String getDistance() {
        if (hasUserNotMovedAtAll()) return DISTANCE_DEFAULT;
        DecimalFormat paceDecimalFormat = new DecimalFormat(FORMAT_DISTANCE);
        paceDecimalFormat.setRoundingMode(RoundingMode.DOWN);
        String formattedTotalDistanceCoveredInKilometers = paceDecimalFormat.format(userLocations.totalDistanceInKilometers());
        return formattedTotalDistanceCoveredInKilometers.equals(FORMATTED_TOTAL_DISTANCE_ZERO) ? DISTANCE_DEFAULT : formattedTotalDistanceCoveredInKilometers;
    }

    public String getNotificationSubText() {
        String elapsedTime = String.format(resources.getString(R.string.format_elapsed_time), getElapsedTime());
        String distance = String.format(resources.getString(R.string.format_distance), getDistance());
        return elapsedTime + "\n" + distance;
    }

    private String formatWithLeadingZero(int timeElement) {
        return String.format(FORMAT_LEADING_ZERO, timeElement);
    }

    public void updateVisitedUserLocations(UserLocations userLocations) {
        this.userLocations = userLocations;
        notifyPropertyChanged(BR.distance);
        notifyPropertyChanged(BR.pace);
    }

    private boolean hasUserNotMovedAtAll() {
        return userLocations == null;
    }

    private boolean hasTimerNotStartedYet() {
        return elapsedTime == null;
    }
}
