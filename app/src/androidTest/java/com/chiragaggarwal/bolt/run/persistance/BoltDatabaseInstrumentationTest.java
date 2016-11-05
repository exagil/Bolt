package com.chiragaggarwal.bolt.run.persistance;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;

import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.RunSchema;
import static com.chiragaggarwal.bolt.run.persistance.BoltDatabaseSchema.UserLocationsSchema;

@RunWith(AndroidJUnit4.class)
public class BoltDatabaseInstrumentationTest {
    private SQLiteDatabase writableDatabase;

    @Before
    public void setup() {
        BoltDatabase boltDatabase = BoltDatabase.getInstance(InstrumentationRegistry.getTargetContext());
        writableDatabase = boltDatabase.getWritableDatabase();
        writableDatabase.beginTransaction();
    }

    @After
    public void tearDown() throws Exception {
        writableDatabase.endTransaction();
        writableDatabase.close();
    }

    @Test
    public void testThatDatabaseIsOpenByDefault() {
        Assert.assertTrue(writableDatabase.isOpen());
    }

    @Test
    public void testThatNumberOfTablesWithDefaultTableWhenCreated() {
        Cursor tablesCursor = writableDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type=?", new String[]{"table"});
        Assert.assertEquals(3, tablesCursor.getCount());
    }

    @Test
    public void testThatItContainsTheRequiredTabled() {
        HashSet<String> tableNamesHashSet = new HashSet<>();
        tableNamesHashSet.add(RunSchema.TABLE_NAME);
        tableNamesHashSet.add(UserLocationsSchema.TABLE_NAME);
        Cursor tablesCursor = writableDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type=?", new String[]{"table"});
        tablesCursor.moveToPosition(1);
        Assert.assertTrue(tableNamesHashSet.contains(tablesCursor.getString(0)));
        tablesCursor.moveToPosition(2);
        Assert.assertTrue(tableNamesHashSet.contains(tablesCursor.getString(0)));
    }
}
