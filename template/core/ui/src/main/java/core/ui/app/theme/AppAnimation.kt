package core.ui.app.theme

import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween

data class AppAnimation(
    val delayShort: Long = 150,
    val durationShort: Int = 250,
    val durationMedium: Int = 500,
    val longDuration: Int = 750,
    val navigationHideDuration: Int = 400
) {
    fun <T> tweenShort() = tween<T>(durationShort)

    fun <T> tweenMedium() = tween<T>(durationMedium)

    fun <T> tweenLong() = tween<T>(longDuration)

    fun <T> springTaDam() = spring<T>(dampingRatio = 0.4f, stiffness = 500f)
}
