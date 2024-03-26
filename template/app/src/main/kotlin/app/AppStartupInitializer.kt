package app

import android.content.Context
import androidx.startup.Initializer
import app.datasource.logging.TimberInitializer
import app.ui.component.image.CoiIImageLoaderInitializer

/**
 * Initializes the application startup configurations and dependencies.
 */
class AppStartupInitializer : AppDependencyInitializer<AppStartupInitializer>() {

    override fun initialize(context: Context): AppStartupInitializer = this

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf(
        CoiIImageLoaderInitializer::class.java,
        TimberInitializer::class.java
    )

}