package core.web3.datasource.blocknative.impl

import core.essentials.http.HttpSource
import core.essentials.misc.utils.GsonUtils
import core.web3.datasource.blocknative.IBlocknativeSource
import core.web3.datasource.blocknative.impl.data.AccountEvent
import core.web3.datasource.blocknative.impl.data.InitializeEvent
import core.web3.datasource.blocknative.impl.data.TransactionEvent
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.wss
import io.ktor.websocket.readBytes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import org.tinylog.Logger

class BlocknativeSource(
    private val apiKey: String,
    private val httpSource: HttpSource
) : IBlocknativeSource {

    private val ktor by lazy { httpSource.ktor }
    private val wsUrl = "wss://api.blocknative.com/v0"

    override suspend fun getChanges(address: String): Flow<TransactionEvent> {
        return flow {
            ktor.wss(wsUrl) {
                sendSerialized(InitializeEvent().apply {
                    dappId = apiKey
                })
                sendSerialized(AccountEvent().apply {
                    dappId = apiKey
                    account = AccountEvent.Account(address)
                })
                incoming.consumeAsFlow()
                    .map { it.readBytes().decodeToString() }
                    .mapNotNull {
                        Logger.info("[TRACK] :: json={}", it)
                        val event = GsonUtils.toObject(it, TransactionEvent::class.java)
                        if (event?.event?.transaction == null) {
                            Logger.debug("event transaction is null :: {}", it)
                        }
                        event
                    }
                    .filter { it.event?.transaction != null }
                    .collect { event -> emit(event) }
            }
        }
    }

}