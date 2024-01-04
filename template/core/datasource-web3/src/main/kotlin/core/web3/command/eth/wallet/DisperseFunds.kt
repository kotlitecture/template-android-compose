package core.web3.command.eth.wallet

import core.web3.IWeb3Context
import core.web3.command.AbstractCommand
import core.web3.model.AssetValue
import core.web3.model.ITransaction
import core.web3.model.Wallet

class DisperseFunds(
    private val from: Wallet,
    private val transfers: List<AssetValue>
) : AbstractCommand<ITransaction>() {

    override suspend fun doExecute(context: IWeb3Context): ITransaction {
        val contract = context.getEthereumInteractor().contractDisperseFunds(from)
        return contract.disperse(transfers)
    }

}