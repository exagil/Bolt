package com.chiragaggarwal.bolt.run.persistance;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.RunSchema;

public class RunContentProvider extends ContentProvider {
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int CODE_ALL_RUNS = 0;

    static {
        uriMatcher.addURI(RunSchema.AUTHORITY, RunSchema.TABLE_NAME, CODE_ALL_RUNS);
    }

    private RunDao runDao;

    @Override
    public boolean onCreate() {
        BoltDatabase boltDatabase = BoltDatabase.getInstance(getContext());
        runDao = new RunDao(boltDatabase);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uriMatcher.match(uri) == CODE_ALL_RUNS) {
            return runDao.query(projection, selection, selectionArgs, sortOrder);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) == CODE_ALL_RUNS) {
            String rowNumber = runDao.insert(values);
            return uri.buildUpon().appendPath(rowNumber).build();
        }
        return uri.buildUpon().appendPath(Dao.INVALID_PATH_ROW).build();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
