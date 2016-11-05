package com.chiragaggarwal.bolt.run.persistance;

import android.database.sqlite.SQLiteDatabase;

public class Dao {
    private final BoltDatabase boltDatabase;

    public Dao(BoltDatabase boltDatabase) {
        this.boltDatabase = boltDatabase;
    }

    public SQLiteDatabase getWritableDatabase() {
        return boltDatabase.getWritableDatabase();
    }
}
