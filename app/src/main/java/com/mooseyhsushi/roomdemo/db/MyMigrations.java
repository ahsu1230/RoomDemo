package com.mooseyhsushi.roomdemo.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;

public class MyMigrations {
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `Park` (`id` INTEGER NOT NULL, "
                    + "`created_at` INTEGER NOT NULL, "
                    + "`updated_at` INTEGER NOT NULL, "
                    + "`name` TEXT, "
                    + "PRIMARY KEY(`id`)"
                    + ")");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Dog "
                    + " ADD COLUMN breed TEXT");
        }
    };
}
