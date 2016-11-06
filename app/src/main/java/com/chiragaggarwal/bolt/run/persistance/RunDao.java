package com.chiragaggarwal.bolt.run.persistance;

import android.content.ContentValues;

public class RunDao extends Dao {

    public RunDao(BoltDatabase boltDatabase) {
        super(boltDatabase);
    }

    public String insert(ContentValues runContentValues) {
        long runRowNumber = insertRun(boltDatabase, runContentValues);
        if (runRowNumber == INVALID_ROW) {
            return NO_ROWS_INSERTED;
        } else {
            return ONE_ROW_INSERTED;
        }
    }

    private long insertRun(BoltDatabase boltDatabase, ContentValues persistableRun) {
        long runRowNumber = boltDatabase.insert(BoltDatabaseSchema.RunSchema.TABLE_NAME, null, persistableRun);
        return runRowNumber;
    }
}
