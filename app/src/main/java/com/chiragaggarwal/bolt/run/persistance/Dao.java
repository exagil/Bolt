package com.chiragaggarwal.bolt.run.persistance;

import android.database.sqlite.SQLiteDatabase;

public abstract class Dao {
    protected static final int INVALID_ROW = -1;
    protected static final String INVALID_PATH_ROW = "-1";
    protected static final String NO_ROWS_INSERTED = "0";
    protected static final String ONE_ROW_INSERTED = "1";
    public final BoltDatabase boltDatabase;

    public Dao(BoltDatabase boltDatabase) {
        this.boltDatabase = boltDatabase;
    }

    public SQLiteDatabase getWritableDatabase() {
        return boltDatabase.getWritableDatabase();
    }
}
