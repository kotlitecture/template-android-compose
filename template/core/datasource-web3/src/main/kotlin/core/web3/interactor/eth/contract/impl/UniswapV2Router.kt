package core.web3.interactor.eth.contract.impl

import core.essentials.misc.extensions.times
import core.web3.IWeb3Context
import core.web3.datasource.ethereum.impl.EthereumTransaction
import core.web3.extensions.weiToEth
import core.web3.interactor.eth.contract.IUniswapFactory
import core.web3.interactor.eth.contract.IUniswapPair
import core.web3.interactor.eth.contract.IUniswapRouter
import core.web3.model.CompositeTransaction
import core.web3.model.ITransaction
import core.web3.model.Wallet
import core.web3.model.eth.UserTx
import org.tinylog.Logger
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.DynamicArray
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.Web3j
import java.math.BigInteger
import java.time.Instant

internal class UniswapV2Router(
    context: IWeb3Context,
    wallet: Wallet,
    web3: Web3j,
) : BaseContract(
    "0x7a250d5630B4cF539739dF2C5dAcb4c659F2488D",
    context,
    wallet,
    web3
), IUniswapRouter {

    override suspend fun weth(): String {
        return "0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2"
    }

    override suspend fun getPair(tokenAddress: String): IUniswapPair {
        val factory = getFactory()
        val weth = weth()
        return factory.getPair(tokenAddress, weth)
    }

    override suspend fun getFactory(): IUniswapFactory {
        val method = "factory"
        val factory = callAddressMethod(
            method = method,
            key = AddressMethodKey(method, contractAddress)
        )!!
        return UniswapFactory(
            factory, context, wallet, web3
        )
    }

    override suspend fun getTokensForEth(amountIn: BigInteger, tokenAddress: String): BigInteger {
        val method = "getAmountsOut"
        val amountsOut: List<Any> = callMethod(
            method = method,
            inputs = listOf(
                Uint256(amountIn), DynamicArray(
                    Address::class.java,
                    listOf(
                        Address(weth()),
                        Address(tokenAddress)
                    )
                )
            ),
            output = OUTPUT_ARRAY_NUMBER
        )!!
        return (amountsOut[1] as Uint256).value
    }

    override suspend fun getEthForTokens(amountIn: BigInteger, tokenAddress: String): BigInteger {
        val method = "getAmountsOut"
        val amountsOut: List<Any> = callMethod(
            method = method,
            inputs = listOf(
                Uint256(amountIn),
                DynamicArray(
                    Address::class.java,
                    listOf(
                        Address(tokenAddress),
                        Address(weth())
                    )
                )
            ),
            output = OUTPUT_ARRAY_NUMBER
        )!!
        return (amountsOut[1] as Uint256).value
    }

    override suspend fun buyTokens(
        amountIn: BigInteger, tokenAddress: String, slippage: Float
    ): ITransaction {
        val method = "swapExactETHForTokensSupportingFeeOnTransferTokens"
        val weth = weth()
        val deadline = Instant.now().plusSeconds(1800).epochSecond.toBigInteger()
        val amountOutMin = getTokensForEth(amountIn, tokenAddress).times(slippage)
        Logger.info("[BUY] min tokens = {} for eth = {} with slippage={}", amountOutMin, amountIn.weiToEth(), slippage)
        val function = Function(
            method,
            listOf(
                Uint256(amountOutMin),
                DynamicArray(
                    Address::class.java,
                    listOf(
                        Address(weth),
                        Address(tokenAddress),
                    )
                ),
                Address(wallet.address),
                Uint256(deadline)
            ),
            emptyList()
        )
        val input = FunctionEncoder.encode(function)
        val userTx = UserTx(
            to = contractAddress,
            from = wallet,
            input = input,
            value = amountIn,
            gasPrice = BigInteger.ZERO,
            gasLimit = BigInteger.ZERO
        )
        return EthereumTransaction(
            userTx = userTx,
            context = context,
            name = wallet.name ?: wallet.address
        )
    }

    override suspend fun sellTokens(
        amountIn: BigInteger, tokenAddress: String, slippage: Float
    ): ITransaction {
        val method = "swapExactTokensForETHSupportingFeeOnTransferTokens"
        val weth = weth()
        val deadline = Instant.now().plusSeconds(1800).epochSecond.toBigInteger()
        val amountOutMin = getEthForTokens(amountIn, tokenAddress).times(slippage)
        val function = Function(
            method,
            listOf(
                Uint256(amountIn),
                Uint256(amountOutMin),
                DynamicArray(
                    Address::class.java,
                    listOf(
                        Address(tokenAddress),
                        Address(weth),
                    )
                ),
                Address(wallet.address),
                Uint256(deadline)
            ),
            emptyList()
        )
        val input = FunctionEncoder.encode(function)
        val userTx = UserTx(
            to = contractAddress,
            from = wallet,
            input = input,
            value = BigInteger.ZERO,
            gasPrice = BigInteger.ZERO,
            gasLimit = BigInteger.ZERO
        )

        val primaryTx = EthereumTransaction(
            userTx = userTx,
            context = context,
            name = wallet.name ?: wallet.address
        )

        return approveSale(tokenAddress)
            ?.let { CompositeTransaction(listOf(it, primaryTx)) }
            ?: primaryTx
    }

    private suspend fun approveSale(tokenAddress: String): ITransaction? {
        return getPair(tokenAddress).tokenNotWeth().approve(contractAddress)
    }

}