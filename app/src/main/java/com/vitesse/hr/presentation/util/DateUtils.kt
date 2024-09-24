package com.vitesse.hr.presentation.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

object DateUtils {

    @OptIn(FormatStringsInDatetimeFormats::class)
    fun dateFrom(value: String, format: String): LocalDate? {
        return try {
            return LocalDate.parse(value, LocalDate.Format { byUnicodePattern(format) })
        } catch (e: Exception) {
            null
        }
    }

    fun LocalDateTime.Companion.now(): LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun LocalDate.Companion.now(): LocalDate {
        return LocalDateTime.now().date
    }

}