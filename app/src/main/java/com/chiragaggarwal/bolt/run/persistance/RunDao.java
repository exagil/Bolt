package com.chiragaggarwal.bolt.run.persistance;

import android.content.ContentValues;
import android.database.Cursor;

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
        return boltDatabase.insert(BoltDatabaseSchema.RunSchema.TABLE_NAME, null, persistableRun);
    }

    public Cursor query(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return boltDatabase.query(BoltDatabaseSchema.RunSchema.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }
}
