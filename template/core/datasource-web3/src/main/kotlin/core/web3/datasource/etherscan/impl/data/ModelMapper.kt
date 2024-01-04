package core.web3.datasource.etherscan.impl.data

import core.essentials.misc.utils.GsonUtils
import core.web3.model.TransactionState
import core.web3.model.eth.Abi
import core.web3.model.eth.ContractSource
import core.web3.model.eth.EthPrice
import org.web3j.crypto.Hash
import org.web3j.utils.Numeric

object ModelMapper {

    fun toSource(address: String, from: GetContract.Response): ContractSource {
        val value = from.items.firstOrNull()
        val abi = value?.abi?.let { toContractAbi(address, it) }
        return ContractSource(
            abi = abi,
            address = address,
            name = value?.name,
            arguments = value?.arguments,
            sourceCode = value?.sourceCode
        )
    }

    fun toStatus(from: GetTransactionStatus.Response): TransactionState {
        val result = from.result ?: return TransactionState.Pending()
        val status = result.status ?: return TransactionState.Pending()
        if (status == "0") return TransactionState.Error("Failed")
        if (status == "1") return TransactionState.Success()
        return TransactionState.Pending()
    }

    fun toContractAbi(address: String, abiSource: String): Abi {
        val abi =
            GsonUtils.toObject(abiSource, GetContractAbi.Response.FunctionDataList()) ?: emptyList()
        val constructor = abi.find { it.type == GetContractAbi.Response.Type.Constructor }
            ?.let(this::toConstructor)
        val functions =
            abi.filter { it.type == GetContractAbi.Response.Type.Function }.map(this::toFunction)
        val events = abi.filter { it.type == GetContractAbi.Response.Type.Event }.map(this::toEvent)
        val errors = abi.filter { it.type == GetContractAbi.Response.Type.Error }.map(this::toError)
        return Abi(
            address = address,
            constructor = constructor,
            functions = functions,
            events = events,
            errors = errors
        )
    }

    fun toPrice(from: GetLastPrice.Response): EthPrice {
        return EthPrice(
            usdRate = from.result.ethusd,
            btcRate = from.result.ethbtc
        )
    }

    private fun toFunction(from: GetContractAbi.Response.FunctionData): Abi.Function {
        val signature = run {
            val result = StringBuilder()
            result.append(from.name)
            result.append("(")
            result.append(from.inputs.joinToString(",") { it.type ?: "" })
            result.append(")")
            result.toString()
        }

        val id = run {
            val input: ByteArray = signature.toByteArray()
            val hash = Hash.sha3(input)
            Abi.getFunctionId(Numeric.toHexString(hash))
        }

        return Abi.Function(
            id = id,
            signature = signature,
            name = from.name ?: "",
            payable = from.isPayable(),
            inputs = toParameters(from.inputs),
            outputs = toParameters(from.outputs)
        )
    }

    private fun toConstructor(from: GetContractAbi.Response.FunctionData): Abi.Constructor {
        return Abi.Constructor(toParameters(from.inputs))
    }

    private fun toError(from: GetContractAbi.Response.FunctionData): Abi.Error {
        return Abi.Error(
            name = from.name ?: "",
            inputs = toParameters(from.inputs)
        )
    }

    private fun toEvent(from: GetContractAbi.Response.FunctionData): Abi.Event {
        return Abi.Event(
            name = from.name ?: "",
            inputs = toParameters(from.inputs)
        )
    }

    private fun toParameters(from: List<GetContractAbi.Response.ParameterData>): List<Abi.Parameter> {
        return from.map(this::toParameter)
    }

    private fun toParameter(from: GetContractAbi.Response.ParameterData): Abi.Parameter {
        return Abi.Parameter(
            name = from.name,
            type = from.type
        )
    }

}