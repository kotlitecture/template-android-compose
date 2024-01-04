package core.web3.command.eth.collection

import core.web3.IWeb3Context
import core.web3.command.AbstractCommand
import core.web3.model.ITransaction
import core.web3.model.Wallet

class SetApprovalForAll(
    private val wallet: Wallet,
    private val contractAddress: String,
    private val operatorAddress: String
) : AbstractCommand<ITransaction?>() {

    override suspend fun doExecute(context: IWeb3Context): ITransaction? {
        val contract = context.getEthereumInteractor().contractErc721(wallet, contractAddress)
        val approved = contract.isApprovedForAll(operatorAddress)
        return if (approved) {
            null
        } else {
            contract.setApprovalForAll(operatorAddress, true)
        }
    }

}