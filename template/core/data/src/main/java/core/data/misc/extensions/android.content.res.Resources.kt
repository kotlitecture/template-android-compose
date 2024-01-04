package core.data.misc.extensions

import android.content.res.Resources
import java.util.Locale

fun Resources.setLocale(locale: Locale) {
    if (Locale.getDefault() != locale) {
        runCatching { Locale.setDefault(locale) }
    }
    val systemConfiguration = Resources.getSystem().configuration
    if (systemConfiguration.locale != locale) {
        runCatching {
            systemConfiguration.setLocale(locale)
            systemConfiguration.setLayoutDirection(locale)
            Resources.getSystem().updateConfiguration(systemConfiguration, displayMetrics)
        }
    }
    if (configuration.locale != locale) {
        runCatching {
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
            updateConfiguration(configuration, displayMetrics)
        }
    }
}