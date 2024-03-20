package core.data.datasource.storage.keyvalue

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

/**
 * Basic key-value data source implementation using SharedPreferences.
 *
 * @param app The application context.
 */
open class BasicKeyValueSource(protected val app: Application) : AbstractKeyValueSource(), KeyValueSource {

    override fun createSharedPreferences(): SharedPreferences {
        return app.getSharedPreferences("key_value_source", Context.MODE_PRIVATE)
    }

}