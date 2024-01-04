package core.web3.command.eth.market

import core.web3.IWeb3Context
import core.web3.command.AbstractCommand
import core.web3.model.eth.GasPrice
import kotlinx.coroutines.flow.Flow

/**
 * Listens for gas price changes.
 */
class GetLiveGasPrice : AbstractCommand<Flow<GasPrice>>() {

    override suspend fun doExecute(context: IWeb3Context): Flow<GasPrice> {
        return context.getEthereumSource().getGasPriceLive()
    }

}