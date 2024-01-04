package core.web3.interactor.eth.contract.impl

import core.essentials.cache.ICacheKey
import core.web3.IWeb3Context
import core.web3.interactor.eth.contract.IContract
import core.web3.model.Wallet
import core.web3.model.eth.Abi
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.DynamicArray
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.Request
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.EthCall
import org.web3j.utils.Numeric
import java.math.BigInteger

internal open class BaseContract(
    override val contractAddress: String,
    protected val context: IWeb3Context,
    protected val wallet: Wallet,
    protected val web3: Web3j,
) : IContract {

    protected val cacheSource = context.getCacheSource()
    private val etherscanSource = context.getEtherscanSource()

    override suspend fun getAbi(input: String?): Abi? {
        val abi = etherscanSource.getContractAbi(contractAddress)
        return abi?.takeIf { input == null || it.getFunctionByInput(input) != null }
    }

    override suspend fun getSource(): core.web3.model.eth.ContractSource? {
        return etherscanSource.getContractSource(contractAddress)
    }

    override suspend fun getImpl(): IContract? {
        val address = cacheSource.get(ImplementationKey(contractAddress)) {
            // https://eips.ethereum.org/EIPS/eip-1967
            val logicAddress = "0x360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbc"
            val beaconAddress = "0xa3f0ad74e5423aebfd80d3ef4346578335a9a72aeaee59ff6cb3582b35133d50"
            val address = web3
                .ethGetStorageAt(
                    contractAddress,
                    Numeric.decodeQuantity(logicAddress),
                    DefaultBlockParameterName.LATEST
                ).send().data
                ?.let { Numeric.toBigInt(it).toString(16) }
                ?: run {
                    web3
                        .ethGetStorageAt(
                            contractAddress,
                            Numeric.decodeQuantity(beaconAddress),
                            DefaultBlockParameterName.LATEST
                        ).send().data
                        ?.let { Numeric.toBigInt(it).toString(16) }
                }
            address?.takeIf { it != "0" }?.let { "0x$it" }
        }
        return address?.let {
            BaseContract(
                contractAddress = it,
                context = context,
                wallet = wallet,
                web3 = web3
            )
        }
    }

    protected fun <T> callMethod(
        method: String,
        inputs: List<Type<*>> = emptyList(),
        output: TypeReference<*>
    ): T? {
        val function = Function(
            method,
            inputs,
            listOf(output)
        )
        val encodedFunction = FunctionEncoder.encode(function)
        val transaction = Transaction.createEthCallTransaction(
            wallet.address, contractAddress, encodedFunction
        )
        val result = web3.ethCall(transaction, DefaultBlockParameterName.LATEST).send()
        val results = FunctionReturnDecoder.decode(result.value, function.outputParameters)
        return results.getOrNull(0)?.value as T?
    }

    protected suspend fun callStringMethod(
        method: String,
        inputs: List<Type<*>> = emptyList(),
        key: ICacheKey<String>? = null
    ): String? {
        if (key != null) {
            return cacheSource.get(key) {
                callMethod(
                    method = method,
                    inputs = inputs,
                    output = OUTPUT_STRING
                )
            }
        } else {
            return callMethod(
                method = method,
                inputs = inputs,
                output = OUTPUT_STRING
            )
        }
    }

    protected suspend fun callAddressMethod(
        method: String,
        inputs: List<Type<*>> = emptyList(),
        key: ICacheKey<String>? = null
    ): String? {
        if (key != null) {
            return cacheSource.get(key) {
                callMethod(
                    method = method,
                    inputs = inputs,
                    output = OUTPUT_ADDRESS
                )
            }
        } else {
            return callMethod(
                method = method,
                inputs = inputs,
                output = OUTPUT_ADDRESS
            )
        }
    }

    protected suspend fun callNumberMethod(
        method: String,
        inputs: List<Type<*>> = emptyList(),
        key: ICacheKey<BigInteger>? = null
    ): BigInteger? {
        if (key != null) {
            return cacheSource.get(key) {
                callMethod(
                    method = method,
                    inputs = inputs,
                    output = OUTPUT_NUMBER
                )
            }
        } else {
            return callMethod(
                method = method,
                inputs = inputs,
                output = OUTPUT_NUMBER
            )
        }
    }

    protected fun ethCallRequest(
        wallet: Wallet,
        method: String,
        contractAddress: String,
        inputs: List<Type<*>> = emptyList(),
        output: TypeReference<*>
    ): EthCallRequest {
        val function = Function(
            method,
            inputs,
            listOf(output)
        )
        val encodedFunction = FunctionEncoder.encode(function)
        val transaction = Transaction.createEthCallTransaction(
            wallet.address, contractAddress, encodedFunction
        )
        val request = web3.ethCall(transaction, DefaultBlockParameterName.LATEST)
        return EthCallRequest(function, request)
    }

    data class StringMethodKey(
        val method: String,
        val contractAddress: String,
        override val ttl: Long = ICacheKey.TTL_60_SECONDS
    ) : ICacheKey<String>

    data class AddressMethodKey(
        val method: String,
        val contractAddress: String,
        override val ttl: Long = ICacheKey.TTL_60_SECONDS
    ) : ICacheKey<String>

    data class TokenStringMethodKey(
        val method: String,
        val tokenId: BigInteger,
        val contractAddress: String,
        override val ttl: Long = ICacheKey.TTL_60_SECONDS
    ) : ICacheKey<String>

    data class BigIntegerMethodKey(
        val method: String,
        val contractAddress: String,
        override val ttl: Long = ICacheKey.TTL_60_SECONDS
    ) : ICacheKey<BigInteger>

    data class ImplementationKey(
        val address: String,
        override val ttl: Long = ICacheKey.TTL_60_SECONDS
    ) : ICacheKey<String>

    data class EthCallRequest(
        private val function: Function,
        private val request: Request<*, EthCall>,
    )

    companion object {
        val OUTPUT_NUMBER: TypeReference<out Uint256> =
            TypeReference.create(Uint256.DEFAULT::class.java)
        val OUTPUT_STRING: TypeReference<out Utf8String> =
            TypeReference.create(Utf8String.DEFAULT::class.java)
        val OUTPUT_ARRAY_NUMBER: TypeReference<out DynamicArray<Uint256>> =
            object : TypeReference<DynamicArray<Uint256>>() {}
        val OUTPUT_BOOLEAN: TypeReference<out Bool> =
            TypeReference.create(Bool.DEFAULT::class.java)
        val OUTPUT_ADDRESS: TypeReference<out Address> =
            TypeReference.create(Address.DEFAULT::class.java)
    }

}