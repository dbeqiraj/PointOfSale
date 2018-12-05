package com.dbeqiraj.pointofsale.utilities

import android.arch.persistence.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    class DateTimeConverter {
        @TypeConverter
        fun fromTimestamp(value: Long?): Date? {
            return if (value == null) null else Date(value)
        }

        @TypeConverter
        fun dateToTimestamp(date: Date?): Long? {
            return (if (date == null) null else date.getTime())!!.toLong()
        }
    }

    fun getHumanReadableDate(date: Date): String {
        val spf = SimpleDateFormat("dd MMM yyyy", Locale.US)
        return spf.format(date)
    }
}