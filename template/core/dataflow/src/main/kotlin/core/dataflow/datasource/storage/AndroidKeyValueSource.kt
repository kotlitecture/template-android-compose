package core.dataflow.datasource.storage

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class AndroidKeyValueSource(private val app: Application) : AbstractKeyValueSource(), KeyValueSource {

    override fun createSharedPreferences(): SharedPreferences {
        return app.getSharedPreferences("key_value_source", Context.MODE_PRIVATE)
    }

}