package app

import android.content.Context
import androidx.startup.Initializer
import app.startup.DependencyInitializer
import app.ui.component.image.CoiIImageLoaderInitializer

class AppStartupInitializer : DependencyInitializer<AppStartupInitializer>() {

    override fun initialize(context: Context): AppStartupInitializer = this

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf(
        CoiIImageLoaderInitializer::class.java
    )

}