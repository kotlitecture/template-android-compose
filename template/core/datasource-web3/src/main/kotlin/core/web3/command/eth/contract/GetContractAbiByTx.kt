package core.web3.command.eth.contract

import core.web3.IWeb3Context
import core.web3.command.AbstractCommand
import core.web3.model.eth.Abi
import core.web3.model.eth.Tx

/**
 * Gets contract abi by tx assuming that tx can be executed on proxy contract.
 */
class GetContractAbiByTx(
    private val tx: Tx
) : AbstractCommand<Abi?>() {

    override suspend fun doExecute(context: IWeb3Context): Abi? {
        val interactor = context.getEthereumInteractor()
        val input = tx.input
        val contract = interactor.contractErc721(context.getEthWallet(), tx.to)
        return (contract.getAbi(input) ?: contract.getImpl()?.getAbi(input))
    }

}