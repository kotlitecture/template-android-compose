package app

import android.content.Context
import androidx.startup.Initializer

/**
 * An abstract class for initializing dependencies.
 *
 * @param T The type of the dependency initializer.
 */
abstract class AppDependencyInitializer<T> : Initializer<T> {

    /**
     * Creates and initializes the dependency.
     *
     * @param context The context of the application.
     * @return The initialized dependency.
     */
    override fun create(context: Context): T & Any {
        return initialize(context)
    }

    /**
     * Specifies the dependencies required for the initialization.
     *
     * @return A list of dependency initializers.
     */
    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()

    /**
     * Initializes the dependency.
     *
     * @param context The context of the application.
     * @return The initialized dependency.
     */
    protected abstract fun initialize(context: Context): T & Any

}