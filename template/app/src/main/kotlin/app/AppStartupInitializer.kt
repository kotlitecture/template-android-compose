package app

import android.content.Context
import androidx.startup.Initializer
import app.startup.DependencyInitializer

class AppStartupInitializer : DependencyInitializer<AppStartupInitializer>() {

    override fun initialize(context: Context): AppStartupInitializer = this

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()

}