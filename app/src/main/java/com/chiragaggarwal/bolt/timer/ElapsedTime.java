package com.chiragaggarwal.bolt.timer;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;

public class ElapsedTime implements Cloneable, Parcelable {
    public static final String TAG = "com.chiragaggarwal.bolt.timer.ElapsedTime";
    private static final int SECONDS_IN_ONE_MINUTE = 60;
    private static final int SECONDS_IN_ONE_HOUR = 3600;
    private static final int ONE_SECOND = 1;
    private int elapsedTimeInSeconds;

    public ElapsedTime() {
        this.elapsedTimeInSeconds = 0;
    }

    @VisibleForTesting
    public ElapsedTime(int elapsedTimeInSeconds) {
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
    public void increaseByOneMinute() {
        elapsedTimeInSeconds += SECONDS_IN_ONE_MINUTE;
    }

    public int hours() {
        return elapsedTimeInSeconds / SECONDS_IN_ONE_HOUR;
    }

    @VisibleForTesting
    public void increaseByOneHour() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(elapsedTimeInSeconds);
    }

    protected ElapsedTime(Parcel in) {
        elapsedTimeInSeconds = in.readInt();
    }

    public static final Creator<ElapsedTime> CREATOR = new Creator<ElapsedTime>() {
        @Override
        public ElapsedTime createFromParcel(Parcel in) {
            return new ElapsedTime(in);
        }

        @Override
        public ElapsedTime[] newArray(int size) {
            return new ElapsedTime[size];
        }
    };
}
