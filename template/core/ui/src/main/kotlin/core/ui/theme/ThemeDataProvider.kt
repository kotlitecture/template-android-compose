package core.ui.theme

import android.graphics.Color
import androidx.activity.SystemBarStyle

/**
 * Abstract class representing a theme data provider.
 * Subclasses should provide the dark mode flag.
 *
 * @param D The type of theme data to be provided by the subclass.
 */
abstract class ThemeDataProvider<D : ThemeData> {

    /** Indicates whether the theme is dark mode or not. */
    protected abstract val dark: Boolean

    protected open fun createSystemBarStyle(): SystemBarStyle {
        return if (dark) {
            SystemBarStyle.dark(Color.TRANSPARENT)
        } else {
            SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        }
    }

    /**
     * Provides the theme data based on the given configuration.
     *
     * @param config The theme configuration.
     * @return The theme data instance.
     */
    abstract fun provide(config: ThemeConfig): D

}