package com.chiragaggarwal.bolt.run.history;

import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;

import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.run.Run;

public class RunDetailsViewModel {
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

    public String getFormattedTotalDistanceInKilometers() {
        return run.formattedTotalDistanceInKilometers();
    }

    public String getElapsedTime() {
        return run.time().toString();
    }

    public int getRating() {
        return run.rating;
    }

    public String getNote() {
        return run.note;
    }

    public int getNoteVisibility() {
        return TextUtils.isEmpty(run.note) ? View.GONE : View.VISIBLE;
    }
}
