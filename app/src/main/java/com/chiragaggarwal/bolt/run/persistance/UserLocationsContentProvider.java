package com.chiragaggarwal.bolt.run.persistance;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.UserLocationsSchema;

public class UserLocationsContentProvider extends ContentProvider {
    private static final UriMatcher uriMatcher = buildUserLocationsUriMatcher();
    private static final int CODE_ALL_USER_LOCATIONS = 0;
    private UserLocationsDao userLocationsDao;


    @Override
    public boolean onCreate() {
        BoltDatabase boltDatabase = BoltDatabase.getInstance(getContext());
        userLocationsDao = new UserLocationsDao(boltDatabase);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
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
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] persistableUserLocations) {
        int numberOfUserLocationsInserted = 0;
        if (uriMatcher.match(uri) == CODE_ALL_USER_LOCATIONS) {
            numberOfUserLocationsInserted = userLocationsDao.bulkInsert(persistableUserLocations);
            notifyDataSetChanged(uri);
        }
        return numberOfUserLocationsInserted;
    }

    private void notifyDataSetChanged(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);
    }

    private static UriMatcher buildUserLocationsUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(UserLocationsSchema.AUTHORITY, UserLocationsSchema.TABLE_NAME, CODE_ALL_USER_LOCATIONS);
        return uriMatcher;
    }
}
