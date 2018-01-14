package com.example.victormotogna.para.dal.local;

import android.arch.persistence.room.TypeConverter;

import com.example.victormotogna.para.model.Category;

import java.util.Date;

/**
 * Created by victormotogna on 1/12/18.
 */

public class TypeConverters {
    @TypeConverter
    public static Long fromDate(Date date) {
        if(date == null) {
            return null;
        }

        return date.getTime();
    }

    @TypeConverter
    public static Date toDate(Long millisSinceEpoch) {
        if (millisSinceEpoch==null) {
            return null;
        }

        return new Date(millisSinceEpoch);
    }

    @TypeConverter
    public static String fromCategory(Category category) {
        return category.toString();
    }

    @TypeConverter
    public static Category toCategory(String category) {
        return Category.valueOf(category);
    }
}
