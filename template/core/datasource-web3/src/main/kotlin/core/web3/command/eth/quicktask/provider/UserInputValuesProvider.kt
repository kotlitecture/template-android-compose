package core.web3.command.eth.quicktask.provider

import core.web3.model.Wallet
import org.tinylog.Logger

data class UserInputValuesProvider(
    private val wallet: Wallet,
    private val provider: IValuesProvider
) : IValuesProvider {

    override fun provide(): List<ParameterValue> {
        val values = provider.provide()
        return values.map { value ->
            val provider = providers.find { it.accept(value) }
            if (provider != null) {
                val newValue = value.copy(value = provider.provide(wallet, value))
                Logger.debug("update value of {} :: {} -> {}", value.parameter.name, value.value, newValue.value)
                newValue
            } else {
                value
            }
        }
    }

    companion object {
        private val providers = listOf<IValueProvider>(
            AddressValueProvider()
        )
    }

}