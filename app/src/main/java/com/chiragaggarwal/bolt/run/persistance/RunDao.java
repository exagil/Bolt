package com.chiragaggarwal.bolt.run.persistance;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class RunDao extends Dao {
    private final UserLocationsDao userLocationsDao;

    public RunDao(BoltDatabase boltDatabase) {
        super(boltDatabase);
        userLocationsDao = new UserLocationsDao(boltDatabase);
    }

    public String insert(ContentValues runContentValues) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        long runRowNumber = insertRun(writableDatabase, runContentValues);
        if (runRowNumber == INVALID_ROW) {
            writableDatabase.endTransaction();
            return NO_ROWS_INSERTED;
        } else {
            writableDatabase.setTransactionSuccessful();
            return ONE_ROW_INSERTED;
        }
    }

    private long insertRun(SQLiteDatabase writableDatabase, ContentValues persistableRun) {
        long runRowNumber = writableDatabase.insert(BoltDatabaseSchema.RunSchema.TABLE_NAME, null, persistableRun);
        return runRowNumber;
    }
}
