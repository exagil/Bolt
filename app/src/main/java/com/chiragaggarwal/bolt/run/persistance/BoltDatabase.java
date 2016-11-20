package com.chiragaggarwal.bolt.run.persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chiragaggarwal.bolt.R;

import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.RunSchema;
import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.UserLocationsSchema;

public class BoltDatabase extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static BoltDatabase boltDatabase;

    public static BoltDatabase getInstance(Context context) {
        if (boltDatabase == null)
            boltDatabase = new BoltDatabase(context, context.getString(R.string.bolt_database_name), null, VERSION);
        return boltDatabase;
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
                UserLocationsSchema._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserLocationsSchema.LATITUDE + " NUMERIC, " +
                UserLocationsSchema.LONGITUDE + " NUMERIC, " +
                UserLocationsSchema.HAS_ACCURACY + " NUMERIC, " +
                UserLocationsSchema.ACCURACY + " NUMERIC, " +
                UserLocationsSchema.HAS_SPEED + " NUMERIC, " +
                UserLocationsSchema.SPEED + " NUMERIC, " +
                UserLocationsSchema.RUN_ID + " INTEGER, " +
                "FOREIGN KEY(" + UserLocationsSchema.RUN_ID + ") REFERENCES " + RunSchema.TABLE_NAME + "(" + RunSchema._ID + ")" +
                ")";
    }

    private String createRunTableSqlStatement() {
        return "CREATE TABLE " + RunSchema.TABLE_NAME + "(" +
                RunSchema._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RunSchema.NOTE + " TEXT, " +
                RunSchema.RATING + " INTEGER, " +
                RunSchema.ELAPSED_TIME_IN_SECONDS + " NUMERIC, " +
                RunSchema.POLYLINE + " TEXT," +
                RunSchema.CREATED_AT + " NUMERIC " +
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

    public synchronized long insert(String table, String nullColumnHack, ContentValues values) {
        return getWritableDatabase().insert(table, nullColumnHack, values);
    }

    public synchronized void beginTransaction() {
        getWritableDatabase().beginTransaction();
    }

    public synchronized void endTransaction() {
        getWritableDatabase().endTransaction();
    }

    public synchronized void setTransactionSuccessful() {
        getWritableDatabase().setTransactionSuccessful();
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return getWritableDatabase().query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
}
