package core.datasource.storage

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class KeyValueSource(private val app: Application) : AbstractKeyValueSource(), IKeyValueSource {

    override fun createSharedPreferences(): SharedPreferences {
        return app.getSharedPreferences("key_value_source", Context.MODE_PRIVATE)
    }

}