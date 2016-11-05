package com.chiragaggarwal.bolt.run.persistance;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.chiragaggarwal.bolt.location.UserLocations;

import io.reactivex.Notification;
import io.reactivex.Observable;

import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.UserLocationsSchema;

public class UserLocationsDao extends Dao {

    public UserLocationsDao(BoltDatabase boltDatabase) {
        super(boltDatabase);
    }

    public Observable<ContentValues> insert(UserLocations userLocationsToInsert) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        return userLocationsToInsert.persistable().
                doOnSubscribe((_disposable) -> writableDatabase.beginTransaction()).
                doOnEach((persistableUserLocation) -> insert(persistableUserLocation, writableDatabase)).
                doOnComplete(() -> {
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                }).
                doOnError((throwable) -> writableDatabase.endTransaction());
    }

    private long insert(Notification<ContentValues> persistableUserLocation, SQLiteDatabase writableDatabase) {
        return writableDatabase.insert(UserLocationsSchema.TABLE_NAME, null, persistableUserLocation.getValue());
    }
}
