package com.mooseyhsushi.roomdemo.db.converters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class ColumnConverters {

    @TypeConverter
    public static String convertIfNull(String value) {
        return value == null ? "" : value;
    }

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
