package core.ui.misc.utils

import android.content.Context
import android.text.format.DateUtils
import core.essentials.misc.extensions.asBigDecimal
import java.math.BigDecimal
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date

object FormatUtils {

    const val DOT = "·"
    const val DASH = "-"
    const val PERCENT = "%"
    const val MULTIPLIER = "×"
    const val APPROX_EQUAL = "≈"

    fun formatDateRelative(date: Date?, context: Context): String {
        if (date == null) {
            return ""
        }
        return DateUtils.getRelativeDateTimeString(
            context,
            date.time,
            DateUtils.DAY_IN_MILLIS,
            DateUtils.WEEK_IN_MILLIS,
            DateUtils.FORMAT_SHOW_TIME
        ).toString()
    }

    fun formatTimeRelative(date: Date?): String? {
        if (date == null) {
            return null
        }
        return DateUtils.getRelativeTimeSpanString(
            date.time,
            System.currentTimeMillis(),
            DateUtils.SECOND_IN_MILLIS
        ).toString()
    }

    fun formatDateShort(date: Date?): String? {
        return date?.let(DateFormat.getDateInstance(DateFormat.SHORT)::format)
    }

    fun getDateMediumFormat(): String {
        return (DateFormat.getDateInstance(DateFormat.MEDIUM) as? SimpleDateFormat)?.toPattern()
            ?: ""
    }

    fun formatDateMedium(date: Date?): String? {
        return date?.let(DateFormat.getDateInstance(DateFormat.MEDIUM)::format)
    }

    fun formatDateTimeMedium(date: Date?): String? {
        return date?.let(
            DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM, DateFormat.MEDIUM
            )::format
        )
    }

    fun formatDateTimeShort(date: Date?): String? {
        return date?.let(
            DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM, DateFormat.SHORT
            )::format
        )
    }

    fun formatPercent(number: Number, withSign: Boolean = false): String {
        val percent = number.asBigDecimal()
        val value = "${percent.toPlainString()}%"
        if (withSign && percent > BigDecimal.ZERO) {
            return "+$value"
        }
        return value
    }

    fun formatTime(seconds: Number): String {
        val value = seconds.toLong()
        val hours = value / 3600
        val minutes = value % 3600 / 60
        val secondsRef = value % 60
        return if (hours > 0) {
            String.format("%02d:%02d:%02d", hours, minutes, secondsRef)
        } else {
            String.format("%02d:%02d", minutes, secondsRef)
        }
    }

    fun formatSeconds(seconds: Number): String {
        val value = seconds.toLong()
        if (value <= 60) {
            return String.format("%02d:%02d", 0, value)
        }
        val minutes = value % 3600 / 60
        val secondsRef = value % 60
        return String.format("%02d:%02d", minutes, secondsRef)
    }

    fun formatCurrency(value: BigDecimal): String {
        val formatter = NumberFormat.getNumberInstance()
        formatter.maximumFractionDigits = value.scale()
        return formatter.format(value)
    }

}
