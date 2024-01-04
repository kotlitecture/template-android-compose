package core.web3.command.eth.market

import core.essentials.misc.extensions.withJobAsync
import core.web3.IWeb3Context
import core.web3.command.AbstractCommand
import core.web3.command.eth.contract.GetMintByTransfer
import core.web3.model.eth.Mint
import core.web3.model.eth.Transfer
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.tinylog.kotlin.Logger

/**
 * Listens all latest mints of any ERC721 contract.
 */
class GetLiveMints : AbstractCommand<Flow<List<Mint>>>() {

    override suspend fun doExecute(context: IWeb3Context): Flow<List<Mint>> {
        return flow {
            val ethereumSource = context.getEthereumSource()
            ethereumSource.getTokenTransfers()
                .catch { Logger.error(it, "GetLiveMints") }
                .map { mints -> mints.map { toAsync(it, context) } }
                .map { jobs -> jobs.awaitAll().mapNotNull { it.getOrNull() } }
                .buffer(Channel.UNLIMITED)
                .collect { emit(it) }
        }
    }

    private suspend fun toAsync(
        transfer: Transfer,
        context: IWeb3Context
    ) = withJobAsync {
        GetMintByTransfer(transfer)
            .executeOptional(context)
    }

}