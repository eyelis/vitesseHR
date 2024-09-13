package com.vitesse.hr.data.converter

import android.net.Uri
import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class Converters {

    @TypeConverter
    fun toDate(value: Int?): LocalDate? {
        return value?.let { LocalDate.fromEpochDays(value) }
    }

    @TypeConverter
    fun fromDate(date: LocalDate?): Int? {
        return date?.toEpochDays()
    }

    @TypeConverter
    fun toUri(value: String?): Uri? {
        return if (value == null) null else Uri.parse(value)
    }

    @TypeConverter
    fun fromUri(uri: Uri?): String? {
        return uri?.toString()
    }
}