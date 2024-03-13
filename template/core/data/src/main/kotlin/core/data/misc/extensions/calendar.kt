package core.data.misc.extensions

import java.util.*

fun Calendar.withDays(days: Int): Calendar {
    add(Calendar.DAY_OF_MONTH, days)
    return this
}

fun Calendar.withSeconds(seconds: Int): Calendar {
    add(Calendar.SECOND, seconds)
    return this
}

fun Calendar.clearTime(): Calendar {
    clear(Calendar.ZONE_OFFSET)
    clear(Calendar.HOUR_OF_DAY)
    clear(Calendar.MINUTE)
    clear(Calendar.SECOND)
    clear(Calendar.MILLISECOND)
    return this
}

fun Calendar.resetTime(): Calendar {
    clear(Calendar.ZONE_OFFSET)
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
    return this
}

fun Calendar.withUtc(): Calendar {
    timeZone = TimeZone.getTimeZone("UTC")
    clear(Calendar.ZONE_OFFSET)
    return this
}

fun Calendar.withDate(millis: Long = System.currentTimeMillis()): Calendar {
    timeInMillis = millis
    return this
}