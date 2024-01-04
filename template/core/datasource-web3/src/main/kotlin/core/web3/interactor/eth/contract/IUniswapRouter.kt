package core.web3.interactor.eth.contract

import core.web3.model.ITransaction
import java.math.BigInteger

/**
 * https://docs.uniswap.org/contracts/v2/reference/smart-contracts/router-02
 * */
interface IUniswapRouter : IContract {

    suspend fun weth(): String

    suspend fun getFactory(): IUniswapFactory

    suspend fun getPair(tokenAddress: String): IUniswapPair

    suspend fun getEthForTokens(amountIn: BigInteger, tokenAddress: String): BigInteger

    suspend fun getTokensForEth(amountIn: BigInteger, tokenAddress: String): BigInteger

    suspend fun buyTokens(amountIn: BigInteger, tokenAddress: String, slippage:Float = 0.8f): ITransaction

    suspend fun sellTokens(amountIn: BigInteger, tokenAddress: String, slippage:Float = 0.7f): ITransaction

}