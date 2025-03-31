package com.mvnh.common.util

import androidx.compose.ui.text.intl.Locale
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val fullDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
private val withoutYearFormatter = DateTimeFormatter.ofPattern("d MMMM", Locale.current.platformLocale)

fun formatDate(date: String): String {
    val fixedDate = date.replace("+0000", "+00:00")
    val parsedDate = LocalDateTime.parse(fixedDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    return parsedDate.format(fullDateFormatter)
}

fun formatDate(timestamp: Long, withoutYear: Boolean): String {
    val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
    return if (withoutYear) {
        dateTime.format(withoutYearFormatter)
    } else {
        if (timestamp != 0L) dateTime.format(fullDateFormatter) else ""
    }
}

fun parseDateToMillis(date: String): Long {
    val localDate = LocalDate.parse(date, fullDateFormatter)
    return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}