package com.mooseyhsushi.roomdemo;

import android.app.Instrumentation;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.mooseyhsushi.roomdemo.db.MyDatabase;
import com.mooseyhsushi.roomdemo.db.MyMigrations;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public MigrationTestHelper helper;

    public ExampleInstrumentedTest() {
        helper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                MyDatabase.class.getCanonicalName(),
                new FrameworkSQLiteOpenHelperFactory());
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.mooseyhsushi.roomdemo", appContext.getPackageName());
    }

    @Test
    public void migrate1To2() throws IOException {
        SupportSQLiteDatabase db = helper.createDatabase("db", 1);

        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
//        db.execSQL(...);

        // Prepare for the next version.
        db.close();

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        db = helper.runMigrationsAndValidate("db", 2, true, MyMigrations.MIGRATION_1_2);

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.
    }

    @Test
    public void migrate2To3() throws IOException {
        SupportSQLiteDatabase db = helper.createDatabase("db", 2);
//        db.execSQL(...);
        db.close();

        db = helper.runMigrationsAndValidate("db", 3, true, MyMigrations.MIGRATION_2_3);
    }

    @Test
    public void migrate3To4() throws IOException {
        SupportSQLiteDatabase db = helper.createDatabase("db", 3);
//        db.execSQL(...);
        db.close();

        db = helper.runMigrationsAndValidate("db", 4, true, MyMigrations.MIGRATION_3_4);
    }
}
