package core.web3.command.eth.market

import core.web3.IWeb3Context
import core.web3.command.AbstractCommand
import core.web3.model.eth.Block
import kotlinx.coroutines.flow.Flow

/**
 * Listens for block changes.
 */
class GetLiveBlock : AbstractCommand<Flow<Block>>() {

    override suspend fun doExecute(context: IWeb3Context): Flow<Block> {
        return context.getEthereumSource().getBlockLive()
    }

}