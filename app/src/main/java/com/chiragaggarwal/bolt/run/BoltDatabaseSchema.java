package com.chiragaggarwal.bolt.run;

import android.provider.BaseColumns;

public interface BoltDatabaseSchema {
    interface UserLocationsSchema extends BaseColumns {
        String TABLE_NAME = "user_locations";
    }

    interface RunSchema extends BaseColumns {
        String TABLE_NAME = "run";
    }
}
