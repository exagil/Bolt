package com.chiragaggarwal.bolt.run.persistance;

import android.net.Uri;
import android.provider.BaseColumns;

public interface BoltDatabaseSchema {
    String AUTHORITY = "com.chiragaggarwal.bolt";

    interface UserLocationsSchema extends BaseColumns {
        String TABLE_NAME = "user_locations";
        String LATITUDE = "latitude";
        String LONGITUDE = "longitude";
        String HAS_ACCURACY = "has_accuracy";
        String ACCURACY = "accuracy";
        String HAS_SPEED = "has_speed";
        String SPEED = "speed";
        String RUN_ID = "run_id";
        String AUTHORITY = BoltDatabaseSchema.AUTHORITY + ".userlocationsprovider";
        Uri ALL_USER_LOCATIONS_URI = new Uri.Builder().scheme("content").authority(UserLocationsSchema.AUTHORITY).appendPath(UserLocationsSchema.TABLE_NAME).build();
    }

    interface RunSchema extends BaseColumns {
        String TABLE_NAME = "run";
        String RATING = "rating";
        String NOTE = "note";
        String AUTHORITY = BoltDatabaseSchema.AUTHORITY + ".runprovider";
        String ELAPSED_TIME_IN_SECONDS = "elapsed_time_in_seconds";
        String CREATED_AT = "created_at";
        Uri ALL_RUNS_RESOURCE_URI = new Uri.Builder().scheme("content").authority(RunSchema.AUTHORITY).appendPath(RunSchema.TABLE_NAME).build();
    }
}
