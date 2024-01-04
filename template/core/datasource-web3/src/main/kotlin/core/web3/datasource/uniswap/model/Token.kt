package core.web3.datasource.uniswap.model

import core.web3.interactor.eth.contract.IContract
import java.math.BigInteger

data class Token(
    val id: String,
    val name: String?,
    val symbol: String,
    val decimals: BigInteger?,
    val totalSupply: BigInteger?
) {
    fun isWeth():Boolean = symbol == IContract.WETH
}