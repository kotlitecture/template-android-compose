package core.ui.theme

/**
 * Abstract class representing a theme data provider.
 * Subclasses should provide the dark mode flag.
 */
abstract class ThemeDataProvider {

    /** Indicates whether the theme is dark mode or not. */
    protected abstract val dark: Boolean

}