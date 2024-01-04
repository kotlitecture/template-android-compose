package core.web3.command.eth.quicktask.provider

import core.web3.model.Wallet

class AddressValueProvider : IValueProvider {

    override fun accept(value: ParameterValue): Boolean {
        return value.parameter.isAddress() && value.value != Wallet.NULL_ADDRESS
    }

    override fun provide(wallet: Wallet, value: ParameterValue): Any {
        return wallet.address
    }
}