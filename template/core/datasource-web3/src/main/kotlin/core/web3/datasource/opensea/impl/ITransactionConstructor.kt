package core.web3.datasource.opensea.impl

import core.web3.model.eth.UserTx

interface ITransactionConstructor {

    fun construct(data: TransactionData): UserTx

}