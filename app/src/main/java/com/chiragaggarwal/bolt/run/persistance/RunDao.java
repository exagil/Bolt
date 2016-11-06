package com.chiragaggarwal.bolt.run.persistance;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.chiragaggarwal.bolt.run.Run;

import java.math.BigInteger;

import io.reactivex.Observable;

public class RunDao extends Dao {
    private final UserLocationsDao userLocationsDao;

    public RunDao(BoltDatabase boltDatabase) {
        super(boltDatabase);
        userLocationsDao = new UserLocationsDao(boltDatabase);
    }

    public Observable<ContentValues> insert(Run run) {
        BigInteger runRowNumber = new BigInteger("0");
        SQLiteDatabase writableDatabase = getWritableDatabase();
        return run.persistable().
                doOnSubscribe((_disposable) -> writableDatabase.beginTransaction()).
                doOnNext((persistableRun) -> runRowNumber.add(insertRun(writableDatabase, persistableRun))).
                doOnComplete(() -> {
                    writableDatabase.setTransactionSuccessful();
                    userLocationsDao.insert(run.userLocations, runRowNumber.longValue());
                }).
                doOnError((_throwable) -> writableDatabase.endTransaction());
    }

    private BigInteger insertRun(SQLiteDatabase writableDatabase, ContentValues persistableRun) {
        long runRowNumber = writableDatabase.insert(BoltDatabaseSchema.RunSchema.TABLE_NAME, null, persistableRun);
        return new BigInteger(String.valueOf(runRowNumber));
    }
}
