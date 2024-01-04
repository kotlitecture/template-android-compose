package core.web3.datasource.opensea.impl

import core.essentials.misc.extensions.takeIfIndex
import core.web3.model.eth.UserTx
import org.tinylog.Logger
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.TypeDecoder
import org.web3j.abi.datatypes.DynamicArray
import org.web3j.abi.datatypes.DynamicStruct
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.StaticStruct
import org.web3j.abi.datatypes.Type
import java.math.BigInteger

class TransactionConstructor : ITransactionConstructor {

    private val FUNC_OPEN = "(("
    private val FUNC_CLOSE = "))"
    private val ARG_COMMA = ","
    private val STRUCT_OPEN = "("
    private val STRUCT_CLOSE = ")[]"

    override fun construct(data: TransactionData): UserTx {
        val funcName = getFunctionName(data.methodSignature)
        val funcArgs = getFunctionParams(data.methodSignature)
        Logger.debug("construct :: funcName={}, funcArgs={}", funcName, funcArgs)
        val params = mutableListOf<Type<*>>()
        var remainingArgs: String? = funcArgs
        data.params.forEach { param ->
            val args = remainingArgs
            if (args != null) {
                remainingArgs = proceed(params, args, param.key, param.value)
            }
        }
        val struct = DynamicStruct(params)
        val function = Function(funcName, listOf(struct), emptyList())
        val input = FunctionEncoder.encode(function)
        return UserTx(
            to = data.to,
            from = data.from,
            input = input,
            value = data.value,
            gasPrice = BigInteger.ZERO,
            gasLimit = BigInteger.ZERO
        )
    }

    private fun getFunctionName(methodSignature: String): String {
        return methodSignature.substring(0, methodSignature.indexOf(FUNC_OPEN))
    }

    private fun getFunctionParams(methodSignature: String): String {
        return methodSignature
            .substring(
                methodSignature.indexOf(FUNC_OPEN) + 2,
                methodSignature.lastIndexOf(FUNC_CLOSE)
            )
            .replace(" ", "")
    }

    private fun proceed(
        params: MutableList<Type<*>>,
        remainingArgs: String,
        key: String,
        value: Any,
    ): String? {
        Logger.debug("proceed remaining :: {}", remainingArgs)
        if (remainingArgs.startsWith(STRUCT_OPEN)) {
            val childArgs = remainingArgs.substring(
                STRUCT_OPEN.length,
                remainingArgs.indexOf(STRUCT_CLOSE)
            )
            val array = value as List<*>
            val arrayTypes = mutableListOf<Type<*>>()
            array.forEach { arrayItem ->
                arrayItem as Map<String, Any>
                val params = mutableListOf<Type<*>>()
                var paramsArgs: String? = childArgs
                arrayItem.forEach { item ->
                    paramsArgs?.let { args ->
                        paramsArgs = proceed(params, args, item.key, item.value)
                    }
                }
                arrayTypes.add(StaticStruct(params))
            }
            params.add(DynamicArray(arrayTypes))
            Logger.debug("proceed child args :: {} - {}={}", childArgs, key, arrayTypes)
            return remainingArgs.substring(childArgs.length + STRUCT_OPEN.length + STRUCT_CLOSE.length + 1)
        } else {
            val comma = remainingArgs.indexOf(ARG_COMMA).takeIfIndex() ?: remainingArgs.length
            val solidityType = remainingArgs.take(comma)
            val normalized = normalize(solidityType, value)
            Logger.debug("proceed solidityType :: {} -> {}={}", solidityType, key, normalized)
            val type = TypeDecoder.instantiateType(solidityType, normalized)
            params.add(type)
            if (remainingArgs.length > comma) {
                return remainingArgs.substring(comma + 1)
            }
        }
        return null
    }

    private fun normalize(solidityType: String, value: Any): Any {
        if (solidityType.startsWith("uint") && value is String) {
            return value.toBigInteger()
        }
        return value
    }

}