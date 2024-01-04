package core.web3.interactor.eth.contract

import core.web3.model.eth.Abi
import core.web3.model.eth.ContractSource

interface IContract {

    val contractAddress: String

    suspend fun getImpl(): IContract?

    suspend fun getSource(): ContractSource?

    suspend fun getAbi(input: String? = null): Abi?

    companion object {
        const val WETH = "WETH"
    }

}