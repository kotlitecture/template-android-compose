package core.web3.datasource.ethereum

import core.web3.model.eth.Block
import core.web3.model.eth.GasPrice
import core.web3.model.eth.Transfer
import core.web3.model.eth.Tx
import core.web3.model.eth.UserTx
import kotlinx.coroutines.flow.Flow
import java.math.BigInteger

interface IEthereumSource {

    suspend fun getBlock(): Block?
    suspend fun getBlock(number: BigInteger): Block?
    suspend fun getBlockLive(): Flow<Block>
    suspend fun getGasPrice(): GasPrice?
    suspend fun getGasPriceLive(): Flow<GasPrice>
    suspend fun getBalance(address: String): BigInteger
    suspend fun isWallet(address: String): Boolean
    suspend fun getBalance(addressList: List<String>): List<BigInteger>
    suspend fun getTokenTransfer(hash: String): Transfer?
    suspend fun getTokenTransfers(addresses: List<String>): Flow<List<Transfer>>
    suspend fun getTokenTransfers(): Flow<List<Transfer>>
    suspend fun getGasLimit(tx: UserTx): BigInteger?
    suspend fun getNonce(address: String): BigInteger
    suspend fun getTx(hash: String): Tx?
    suspend fun commit(tx: UserTx): String

}