package app.datasource.storage.keyvalue

import android.app.Application
import core.data.datasource.storage.keyvalue.BasicKeyValueSource

/**
 * Decorator class for working with key-value storage on the app level.
 *
 * Can provide extra methods without impacting facade implementations.
 */
class AppKeyValueSource(app: Application) : BasicKeyValueSource(app)