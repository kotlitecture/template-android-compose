package core.web3.datasource.etherscan

import core.web3.model.TransactionState
import core.web3.model.eth.Abi
import core.web3.model.eth.ContractSource
import core.web3.model.eth.EthPrice

interface IEtherscanSource {

    suspend fun getTransactionState(hash: String): TransactionState

    suspend fun getContractSource(address: String): ContractSource?

    suspend fun getContractAbi(address: String): Abi?

    suspend fun getEthPrice(): EthPrice


}