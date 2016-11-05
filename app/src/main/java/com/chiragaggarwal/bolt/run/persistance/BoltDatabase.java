package com.chiragaggarwal.bolt.run.persistance;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chiragaggarwal.bolt.R;

public class BoltDatabase extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    public static BoltDatabase getInstance(Context context) {
        return new BoltDatabase(context, context.getString(R.string.bolt_database_name), null, VERSION);
    }

    private BoltDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private BoltDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createRunTableSqlStatement());
        db.execSQL(createUserLocationsTableSqlStatement());
    }

    private String createUserLocationsTableSqlStatement() {
        return "CREATE TABLE " + BoltDatabaseSchema.UserLocationsSchema.TABLE_NAME + "(" +
                BoltDatabaseSchema.UserLocationsSchema._ID + " INTEGER PRIMARY KEY" +
                ")";
    }

    private String createRunTableSqlStatement() {
        return "CREATE TABLE " + BoltDatabaseSchema.RunSchema.TABLE_NAME + "(" +
                BoltDatabaseSchema.RunSchema._ID + " INTEGER PRIMARY KEY" +
                ")";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
