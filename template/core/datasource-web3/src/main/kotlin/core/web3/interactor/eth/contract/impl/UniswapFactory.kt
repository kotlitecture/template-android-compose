package core.web3.interactor.eth.contract.impl

import core.essentials.cache.ICacheKey
import core.web3.IWeb3Context
import core.web3.interactor.eth.contract.IUniswapFactory
import core.web3.interactor.eth.contract.IUniswapPair
import core.web3.model.Wallet
import org.web3j.abi.datatypes.Address
import org.web3j.protocol.Web3j

internal class UniswapFactory(
    override val contractAddress: String,
    context: IWeb3Context,
    wallet: Wallet,
    web3: Web3j,
) : BaseContract(
    contractAddress,
    context,
    wallet,
    web3
), IUniswapFactory {

    override suspend fun getPair(tokenA: String, tokenB: String): IUniswapPair {
        val method = "getPair"
        val pairAddress = callAddressMethod(
            method = method,
            inputs = listOf(Address(tokenA), Address(tokenB)),
            key = GetPairKey(tokenA, tokenB, contractAddress)
        )!!
        return UniswapPair(
            pairAddress,
            context,
            wallet,
            web3
        )
    }

    data class GetPairKey(
        val tokenA: String,
        val tokenB: String,
        val contractAddress: String,
        override val ttl: Long = ICacheKey.TTL_UNLIMITED
    ) : ICacheKey<String>

}