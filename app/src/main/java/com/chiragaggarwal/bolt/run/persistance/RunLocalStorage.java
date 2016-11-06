package com.chiragaggarwal.bolt.run.persistance;

import android.content.Context;
import android.net.Uri;

import com.chiragaggarwal.bolt.run.Run;

import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.RunSchema;
import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.UserLocationsSchema;

public class RunLocalStorage {
    private Context context;

    public RunLocalStorage(Context context) {
        this.context = context;
    }

    public boolean insertRun(Run run) {
        Uri resultUri = context.getContentResolver().insert(allRunsResourceUri(), run.persistable());
        String runRowNumber = resultUri.getLastPathSegment();
        if (runRowNumber.equals(Dao.INVALID_PATH_ROW)) {
            return false;
        }
        Long runRowNumberValue = new Long(runRowNumber);
        context.getContentResolver().bulkInsert(allUserLocationsUri(), run.persistableUserLocations(runRowNumberValue.longValue()));
        return true;
    }

    private Uri allUserLocationsUri() {
        return new Uri.Builder().scheme("content").authority(UserLocationsSchema.AUTHORITY).appendPath(UserLocationsSchema.TABLE_NAME).build();
    }

    private Uri allRunsResourceUri() {
        return new Uri.Builder().scheme("content").authority(RunSchema.AUTHORITY).appendPath(RunSchema.TABLE_NAME).build();
    }
}
