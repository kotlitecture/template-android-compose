package core.data.datasource.storage.keyvalue

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class BasicKeyValueSource(private val app: Application) : AbstractKeyValueSource(), KeyValueSource {

    override fun createSharedPreferences(): SharedPreferences {
        return app.getSharedPreferences("key_value_source", Context.MODE_PRIVATE)
    }

}