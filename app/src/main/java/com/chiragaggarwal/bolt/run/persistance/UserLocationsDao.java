package com.chiragaggarwal.bolt.run.persistance;

import android.content.ContentValues;

import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.UserLocationsSchema;

public class UserLocationsDao extends Dao {

    public UserLocationsDao(BoltDatabase boltDatabase) {
        super(boltDatabase);
    }

    public int bulkInsert(ContentValues[] persistableUserLocations) {
        boltDatabase.beginTransaction();
        int totalInsertedRows = 0;
        for (ContentValues contentValues : persistableUserLocations) {
            long rowIdOfInsertedUserLocation = boltDatabase.insert(UserLocationsSchema.TABLE_NAME, null, contentValues);
            if (rowIdOfInsertedUserLocation == INVALID_ROW) {
                boltDatabase.endTransaction();
                return totalInsertedRows;
            } else {
                totalInsertedRows += 1;
            }
        }
        boltDatabase.setTransactionSuccessful();
        boltDatabase.endTransaction();
        return totalInsertedRows;
    }
}
