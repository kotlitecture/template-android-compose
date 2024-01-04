package core.web3.interactor.eth.contract.impl

import core.web3.IWeb3Context
import core.web3.interactor.eth.contract.IErc20
import core.web3.interactor.eth.contract.IUniswapPair
import core.web3.model.Wallet
import org.web3j.protocol.Web3j

internal class UniswapPair(
    override val contractAddress: String,
    context: IWeb3Context,
    wallet: Wallet,
    web3: Web3j,
) : BaseContract(
    contractAddress,
    context,
    wallet,
    web3
), IUniswapPair {

    override suspend fun tokenNotWeth(): IErc20 {
        val token = token0()
        return if (token.isWeth()) token1() else token
    }

    override suspend fun token0(): IErc20 {
        val method = "token0"
        val address = callAddressMethod(
            method = method,
            key = AddressMethodKey(method, contractAddress)
        )!!.lowercase()
        return Erc20(
            address,
            context,
            wallet,
            web3
        )
    }

    override suspend fun token1(): IErc20 {
        val method = "token1"
        val address = callAddressMethod(
            method = method,
            key = AddressMethodKey(method, contractAddress)
        )!!.lowercase()
        return Erc20(
            address,
            context,
            wallet,
            web3
        )
    }

}