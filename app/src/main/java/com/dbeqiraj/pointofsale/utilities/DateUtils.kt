package com.dbeqiraj.pointofsale.utilities

import android.arch.persistence.room.TypeConverter
import android.content.SharedPreferences
import com.dbeqiraj.pointofsale.R
import com.dbeqiraj.pointofsale.ui.home.adapter.ReceiptsAdapter
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

    fun getHumanReadableDate(date: Date, dateFormat: String): String {
        val spf = SimpleDateFormat(dateFormat, Locale.US)
        return spf.format(date)
    }
}