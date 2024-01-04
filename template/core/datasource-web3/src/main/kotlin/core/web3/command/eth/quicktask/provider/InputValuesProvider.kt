package core.web3.command.eth.quicktask.provider

import core.essentials.exception.DataException
import core.web3.model.eth.Abi
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Array
import org.web3j.abi.datatypes.Type

data class InputValuesProvider(
    private val input: String,
    private val functionId: String,
    private val functionInputs: List<Pair<String, String>>,
) : IValuesProvider {

    override fun provide(): List<ParameterValue> {
        val argsString = input.substring(functionId.length)
        val types = functionInputs.map { TypeReference.makeTypeReference(it.second) }
        val values = FunctionReturnDecoder.decode(argsString, types)
        if (values.size != functionInputs.size) throw DataException(msg = "wrong decoded values")
        return values
            .map { getValue(it) }
            .mapIndexed { index, value ->
                ParameterValue(
                    functionInputs[index].let { Abi.Parameter(it.first, it.second) },
                    value
                )
            }
    }

    private fun getValue(type: Type<*>): Any? {
        return if (type is Array<*>) {
            type.value.map { getValue(it) }
        } else {
            type.value
        }
    }

}