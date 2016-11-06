package com.chiragaggarwal.bolt.run.persistance;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.chiragaggarwal.bolt.location.UserLocations;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.UserLocationsSchema;

public class UserLocationsDao extends Dao {

    public UserLocationsDao(BoltDatabase boltDatabase) {
        super(boltDatabase);
    }

    public Observable<ContentValues> insert(UserLocations userLocationsToInsert, long rowNumber) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        return userLocationsToInsert.persistable().
                map((contentValues) -> {
                    contentValues.put(UserLocationsSchema.RUN_ID, rowNumber);
                    return contentValues;
                }).
                doOnSubscribe((_disposable) -> writableDatabase.beginTransaction()).
                doOnEach((persistableUserLocation) -> insert(persistableUserLocation, writableDatabase)).
                doOnComplete(() -> {
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                }).
                doOnEach(new Consumer<Notification<ContentValues>>() {
                    @Override
                    public void accept(Notification<ContentValues> contentValuesNotification) throws Exception {
                        writableDatabase.endTransaction();
                    }
                });
        // doOnError((throwable) -> writableDatabase.endTransaction());
    }

    private long insert(Notification<ContentValues> persistableUserLocation, SQLiteDatabase writableDatabase) {
        return writableDatabase.insert(UserLocationsSchema.TABLE_NAME, null, persistableUserLocation.getValue());
    }
}
