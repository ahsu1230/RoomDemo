package com.mooseyhsushi.roomdemo.db.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "created_at")
    public long createdAt;

    @ColumnInfo(name = "updated_at")
    public long updatedAt;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "middle_name")
    public String middleName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    public int age;
    public String email;

    @Ignore
    public String extra;
}