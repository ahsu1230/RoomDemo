package com.mooseyhsushi.roomdemo.db.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
            parentColumns = "id",
            childColumns = "owner_id"),
        indices = {@Index(value={"owner_id"})})
public class Dog {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "created_at")
    public long createdAt;

    @ColumnInfo(name = "updated_at")
    public long updatedAt;

    @ColumnInfo(name = "owner_id")
    public long ownerId;

    public String name;
    public int age;

    /* Added for Migration 2-3 */
    public String breed;

    /* Added for Migration 3-4 */
    @ColumnInfo(name = "hair_color")
    public String hairColor;
}
