package core.dataflow.startup

import android.content.Context
import androidx.startup.Initializer
import core.data.startup.InitializerEntryPoint

abstract class DependencyInitializer<T> : Initializer<T> {

    override fun create(context: Context): T & Any {
        InitializerEntryPoint.resolve(context)
        return initialize(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()

    protected abstract fun initialize(context: Context): T & Any

}