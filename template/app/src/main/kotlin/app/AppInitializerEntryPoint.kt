package app

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

/**
 * Entry point interface for initializing the app.
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppInitializerEntryPoint {

    companion object {
        /**
         * Resolves the entry point from the given context.
         *
         * @param context The context from which to resolve the entry point.
         * @return The resolved entry point.
         * @throws IllegalStateException if the application context is null.
         */
        fun resolve(context: Context): AppInitializerEntryPoint {
            val appContext = context.applicationContext ?: throw IllegalStateException()
            return EntryPointAccessors.fromApplication(
                appContext,
                AppInitializerEntryPoint::class.java
            )
        }
    }

}