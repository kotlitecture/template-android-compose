package core.di

import android.app.Application
import core.datasource.analytics.IAnalyticsSource
import core.datasource.config.IConfigSource
import core.datasource.encryption.EncryptedObjectSource
import core.datasource.encryption.IEncryptedObjectSource
import core.datasource.network.INetworkSource
import core.datasource.network.NetworkSource
import core.datasource.storage.EncryptedKeyValueSource
import core.datasource.storage.IEncryptedKeyValueSource
import core.datasource.storage.IKeyValueSource
import core.datasource.storage.KeyValueSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Date
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class Provides {

    @Provides
    @Singleton
    fun networkSource(
        app: Application
    ): INetworkSource {
        return NetworkSource(app)
    }

    @Provides
    @Singleton
    fun analyticsSource(): IAnalyticsSource {
        return TestAnalyticsSource()
    }

    @Provides
    @Singleton
    fun configSource(): IConfigSource {
        return TestConfigSource()
    }

    @Provides
    @Singleton
    fun keyValueSource(app: Application): IKeyValueSource {
        return KeyValueSource(app)
    }

    @Provides
    @Singleton
    fun encryptedKeyValueSource(app: Application): IEncryptedKeyValueSource {
        return EncryptedKeyValueSource(app)
    }

    @Provides
    @Singleton
    fun encryptedObjectSource(source: IEncryptedKeyValueSource): IEncryptedObjectSource {
        return EncryptedObjectSource(source)
    }

}

class TestConfigSource : IConfigSource {

    override fun <T> get(key: String, type: Class<T>, defaultValue: () -> T): T {
        throw AbstractMethodError("get error for key $key")
    }

}

class TestAnalyticsSource : IAnalyticsSource {

    val events = mutableListOf<Event>()

    override fun setUserId(id: String?) {

    }

    override fun setUserProperty(key: String, value: String?) {

    }

    override fun onError(event: String, error: Throwable) {

    }

    override fun onEvent(event: String, params: Map<String, String>) {
        events.add(Event(event, params = params))
    }

    override fun onScreenView(screenName: String, params: Map<String, String>) {
        events.add(Event(screenName, params = params))
    }

    data class Event(
        val name: String, val date: Date = Date(), val params: Map<String, String>
    )
}