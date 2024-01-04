package core.web3.command.eth.wallet

import core.web3.IWeb3Context
import core.web3.command.AbstractCommand
import core.web3.model.Blockchain
import core.web3.model.Wallet

class GenerateWallets(
    private val withNumberOfWallets: Int
) : AbstractCommand<List<Wallet>>() {

    override suspend fun doExecute(context: IWeb3Context): List<Wallet> {
        return (0 until withNumberOfWallets).map { Blockchain.ETH.generateWallet() }
    }

}