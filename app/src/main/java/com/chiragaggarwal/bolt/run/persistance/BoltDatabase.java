package com.chiragaggarwal.bolt.run.persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chiragaggarwal.bolt.R;

import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.RunSchema;
import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.UserLocationsSchema;

public class BoltDatabase extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    public static BoltDatabase getInstance(Context context) {
        return new BoltDatabase(context, context.getString(R.string.bolt_database_name), null, VERSION);
    }

    private BoltDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createRunTableSqlStatement());
        db.execSQL(createUserLocationsTableSqlStatement());
    }

    private String createUserLocationsTableSqlStatement() {
        return "CREATE TABLE " + UserLocationsSchema.TABLE_NAME + "(" +
                UserLocationsSchema._ID + " INTEGER PRIMARY KEY, " +
                UserLocationsSchema.LATITUDE + " NUMERIC, " +
                UserLocationsSchema.LONGITUDE + " NUMERIC, " +
                UserLocationsSchema.HAS_ACCURACY + " NUMERIC, " +
                UserLocationsSchema.ACCURACY + " NUMERIC, " +
                UserLocationsSchema.HAS_SPEED + " NUMERIC, " +
                UserLocationsSchema.SPEED + " NUMERIC" +
                ")";
    }

    private String createRunTableSqlStatement() {
        return "CREATE TABLE " + RunSchema.TABLE_NAME + "(" +
                RunSchema._ID + " INTEGER PRIMARY KEY" +
                ")";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void reset() {
        getWritableDatabase().execSQL(dropRunTableSqlStatement());
        getWritableDatabase().execSQL(dropUserLocationsTableSqlStatement());
        onCreate(getWritableDatabase());
    }

    private String dropUserLocationsTableSqlStatement() {
        return "DROP TABLE IF EXISTS " + UserLocationsSchema.TABLE_NAME;
    }

    private String dropRunTableSqlStatement() {
        return "DROP TABLE IF EXISTS " + RunSchema.TABLE_NAME;
    }
}
