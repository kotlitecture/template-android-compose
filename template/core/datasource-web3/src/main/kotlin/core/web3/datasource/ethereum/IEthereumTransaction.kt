package core.web3.datasource.ethereum

import core.web3.model.ITransaction
import core.web3.model.eth.UserTx

interface IEthereumTransaction : ITransaction {

    /**
     * Returns original transaction.
     */
    suspend fun getRaw(): UserTx

    /**
     * Returns raw transaction with market parameters for gasPrice and gasLimit.
     */
    suspend fun prepareRaw(): UserTx

}