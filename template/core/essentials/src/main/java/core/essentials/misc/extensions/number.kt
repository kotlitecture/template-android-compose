package core.essentials.misc.extensions

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

fun Int.takeIfIndex(): Int? = this.takeIf { it != -1 }

fun BigDecimal.safeDivide(divisor: BigDecimal, scale: Int = scale()): BigDecimal {
    if (divisor == BigDecimal.ZERO) return this
    return divide(divisor, scale, RoundingMode.HALF_UP)
}

fun Number.asBigDecimal(
    withScale: Int = 2,
    withStripTrailingZeros: Boolean = true
): BigDecimal {
    var decimal = if (this is BigDecimal) this else BigDecimal(toString())
    decimal = decimal.setScale(withScale, RoundingMode.HALF_UP)
    if (withStripTrailingZeros) {
        val fractional = decimal.remainder(BigDecimal.ONE)
        decimal = if (fractional == BigDecimal.ZERO) {
            decimal.setScale(withScale, RoundingMode.HALF_UP)
        } else {
            decimal.stripTrailingZeros()
        }
    }
    return decimal
}

fun BigInteger.times(times: Float): BigInteger {
    if (times == 1f) return this
    return toBigDecimal().times(times.toBigDecimal()).toBigInteger()
}

fun BigDecimal.equalsZero(): Boolean = asBigDecimal().compareTo(BigDecimal.ZERO) == 0

fun BigDecimal.equalsOne(): Boolean = compareTo(BigDecimal.ONE) == 0

val max = Long.MAX_VALUE.toBigInteger()

fun BigInteger.toSafeLong(): Long {
    return if (this > max) max.toLong() else toLong()
}