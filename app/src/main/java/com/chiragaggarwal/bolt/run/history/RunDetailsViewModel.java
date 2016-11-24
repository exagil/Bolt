package com.chiragaggarwal.bolt.run.history;

import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.run.Run;

public class RunDetailsViewModel extends BaseObservable {
    private Run run;
    private Resources resources;

    public RunDetailsViewModel(Run run, Resources resources) {
        this.run = run;
        this.resources = resources;
    }

    public String shareText() {
        return resources.getString(R.string.key_date) +
                run.formattedDate() +
                "\n" +
                resources.getString(R.string.key_distance) +
                run.formattedTotalDistanceInKilometers();
    }

    @Bindable
    public String getFormattedTotalDistanceInKilometers() {
        return run.formattedTotalDistanceInKilometers();
    }

    @Bindable
    public String getElapsedTime() {
        return run.time().toString();
    }

    @Bindable
    public int getRating() {
        return run.rating;
    }

    @Bindable
    public String getNote() {
        return run.note;
    }
}
