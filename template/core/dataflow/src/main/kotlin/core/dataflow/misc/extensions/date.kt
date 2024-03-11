package core.dataflow.misc.extensions

import java.math.BigInteger
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

fun Date.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
}

fun Date.clearTime(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
        .clearTime()
        .time
}

fun Date.resetTime(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
        .resetTime()
        .time
}

fun Date.withStartOfTheDay(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
        .clearTime()
        .time
}

fun Date.withEndOfTheDay(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
        .resetTime()
        .withDays(1)
        .withSeconds(-1)
        .time

}

fun Date.withDays(days: Int): Date {
    return withHours(days * 24)
}

fun Date.withHours(hours: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.HOUR, hours)
    return calendar.time
}

fun Date.withMinutes(minutes: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.MINUTE, minutes)
    return calendar.time
}

fun Date.getRemainingTimeOfCurrentHour(): Long {
    val calendar = Calendar.getInstance()
    calendar.time = this
    val minutes = 60L - calendar.get(Calendar.MINUTE)
    val seconds = 60L - calendar.get(Calendar.SECOND)
    return TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds)
}

fun BigInteger.unixToDate(): Date = Date(toLong() * 1000L)