package com.mooseyhsushi.roomdemo.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mooseyhsushi.roomdemo.db.models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    public long insertOne(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public List<Long> insertMany(List<User> users);

    @Update
    public int update(User... users);

    @Delete
    public int delete(User... users);

    @Query("SELECT * FROM user")
    public List<User> getAll();

    @Query("SELECT * FROM user WHERE id=:userId")
    public User getById(long userId);
}
