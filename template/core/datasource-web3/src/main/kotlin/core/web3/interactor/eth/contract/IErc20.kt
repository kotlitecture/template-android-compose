package core.web3.interactor.eth.contract

import core.web3.model.ITransaction
import java.math.BigInteger

interface IErc20 : IContract {

    suspend fun isWeth(): Boolean

    suspend fun name(): String?

    suspend fun owner(): String?

    suspend fun symbol(): String?

    suspend fun decimals(): BigInteger?

    suspend fun totalSupply(): BigInteger?

    suspend fun allowance(spender: String): BigInteger?

    suspend fun balanceOf(address: String): BigInteger?

    suspend fun approve(spender: String, amount: BigInteger): ITransaction

    suspend fun approve(spender: String): ITransaction?

}