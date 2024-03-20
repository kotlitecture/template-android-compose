package app.datasource.network

import android.app.Application
import core.data.datasource.network.BasicNetworkSource

/**
 * Decorator class for working with network.
 *
 * Can provide extra methods without impacting facade implementations.
 */
class AppNetworkSource(app: Application) : BasicNetworkSource(app)