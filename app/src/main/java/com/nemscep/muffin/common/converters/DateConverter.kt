package com.nemscep.muffin.common.converters

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun fromJson(date: Long): Date = Date(date)

    @TypeConverter
    fun toJson(date: Date): Long = date.time
}
