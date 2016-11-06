package com.chiragaggarwal.bolt.run.persistance;

import android.provider.BaseColumns;

public interface BoltDatabaseSchema {
    interface UserLocationsSchema extends BaseColumns {
        String TABLE_NAME = "user_locations";
        String LATITUDE = "latitude";
        String LONGITUDE = "longitude";
        String HAS_ACCURACY = "has_accuracy";
        String ACCURACY = "accuracy";
        String HAS_SPEED = "has_speed";
        String SPEED = "speed";
        String RUN_ID = "run_id";
    }

    interface RunSchema extends BaseColumns {
        String TABLE_NAME = "run";
        String RATING = "rating";
        String NOTE = "note";
    }
}
