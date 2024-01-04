package core.web3.interactor.eth.contract

import core.web3.model.AssetValue
import core.web3.model.ITransaction

interface IDisperseFunds : IContract {

    suspend fun disperse(to: List<AssetValue>): ITransaction

}