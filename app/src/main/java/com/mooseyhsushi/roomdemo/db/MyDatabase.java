package com.mooseyhsushi.roomdemo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.mooseyhsushi.roomdemo.db.converters.ColumnConverters;
import com.mooseyhsushi.roomdemo.db.dao.DogDao;
import com.mooseyhsushi.roomdemo.db.dao.UserDao;
import com.mooseyhsushi.roomdemo.db.models.Dog;
import com.mooseyhsushi.roomdemo.db.models.Park;
import com.mooseyhsushi.roomdemo.db.models.User;

@Database(version = 4, entities = {User.class, Dog.class, Park.class})
@TypeConverters({ColumnConverters.class})
public abstract class MyDatabase extends RoomDatabase {
    public static String DATABASE_NAME = "db";

    public abstract UserDao getUserDao();
    public abstract DogDao getDogDao();

    public static MyDatabase get(Context context) {
        return Room.databaseBuilder(context, MyDatabase.class, DATABASE_NAME)
                .addMigrations(MyMigrations.MIGRATION_1_2,
                        MyMigrations.MIGRATION_2_3,
                        MyMigrations.MIGRATION_3_4)
                .build();
    }
}
