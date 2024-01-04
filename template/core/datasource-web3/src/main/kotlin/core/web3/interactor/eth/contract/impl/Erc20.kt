package core.web3.interactor.eth.contract.impl

import core.essentials.cache.ICacheKey
import core.web3.IWeb3Context
import core.web3.datasource.ethereum.IEthereumTransaction
import core.web3.datasource.ethereum.impl.EthereumTransaction
import core.web3.interactor.eth.contract.IErc20
import core.web3.model.ITransaction
import core.web3.model.Wallet
import core.web3.model.eth.UserTx
import org.tinylog.Logger
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.Web3j
import java.math.BigInteger

internal class Erc20(
    contractAddress: String,
    context: IWeb3Context,
    wallet: Wallet,
    web3: Web3j,
) : BaseContract(
    contractAddress,
    context,
    wallet,
    web3
), IErc20 {

    override suspend fun isWeth(): Boolean {
        return contractAddress.equals("0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2", true)
    }

    override suspend fun name(): String? {
        return callStringMethod(
            method = "name",
            key = StringMethodKey("name", contractAddress)
        )
    }

    override suspend fun owner(): String? {
        return callAddressMethod(
            method = "owner",
            key = AddressMethodKey("owner", contractAddress)
        )
    }

    override suspend fun symbol(): String? {
        return callStringMethod(
            method = "symbol",
            key = StringMethodKey("symbol", contractAddress)
        )
    }

    override suspend fun decimals(): BigInteger? {
        val method = "decimals"
        return callNumberMethod(
            method = method,
            key = BigIntegerMethodKey(method, contractAddress)
        )
    }

    override suspend fun totalSupply(): BigInteger? {
        return callNumberMethod(
            method = "totalSupply"
        )
    }

    override suspend fun balanceOf(address: String): BigInteger? {
        return callNumberMethod(
            method = "balanceOf",
            inputs = listOf(Address(address))
        )
    }

    override suspend fun approve(spender: String, amount: BigInteger): ITransaction {
        val key = ApproveKey(wallet.address, spender, contractAddress)
        return cacheSource.get(key) {
            val function = Function(
                "approve",
                listOf(Address(spender), Uint256(amount)),
                emptyList()
            )
            val input = FunctionEncoder.encode(function)
            val tx = UserTx(
                to = contractAddress,
                from = wallet,
                input = input,
                value = BigInteger.ZERO,
                gasPrice = BigInteger.ZERO,
                gasLimit = BigInteger.ZERO
            )
            EthereumTransaction(tx, context)
        }!!
    }

    override suspend fun approve(spender: String): ITransaction? {
        val amount = balanceOf(wallet.address) ?: return null
        val allowance = allowance(spender) ?: BigInteger.ZERO
        if (allowance < amount) {
            Logger.info("[SELL] approveSale :: allowance={}, amount={}", allowance, amount)
            return approve(spender, amount)
        }
        return null
    }

    override suspend fun allowance(spender: String): BigInteger? {
        val key = AllowanceKey(wallet.address, contractAddress, spender)
        return cacheSource.get(key) {
            callMethod(
                method = "allowance",
                inputs = listOf(Address(wallet.address), Address(spender)),
                output = OUTPUT_NUMBER
            ) as? BigInteger
        }
    }

    private data class ApproveKey(
        val walletAddress: String,
        val spenderAddress: String,
        val contractAddress: String,
        override val ttl: Long = ICacheKey.TTL_UNLIMITED
    ) : ICacheKey<IEthereumTransaction>

    private data class AllowanceKey(
        val walletAddress: String,
        val contractAddress: String,
        val operatorAddress: String,
        override val ttl: Long = ICacheKey.TTL_5_SECONDS
    ) : ICacheKey<BigInteger>


}