package com.mooseyhsushi.roomdemo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mooseyhsushi.roomdemo.db.dao.DogDao;
import com.mooseyhsushi.roomdemo.db.dao.UserDao;
import com.mooseyhsushi.roomdemo.db.models.Dog;
import com.mooseyhsushi.roomdemo.db.models.User;

@Database(version = 1, entities = {User.class, Dog.class})
public abstract class MyDatabase extends RoomDatabase {
    public static String DATABASE_NAME = "db";

    public abstract UserDao getUserDao();
    public abstract DogDao getDogDao();
}
