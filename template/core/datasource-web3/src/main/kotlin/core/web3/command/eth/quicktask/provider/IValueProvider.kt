package core.web3.command.eth.quicktask.provider

import core.web3.model.Wallet

interface IValueProvider {

    fun accept(value: ParameterValue): Boolean

    fun provide(wallet: Wallet, value: ParameterValue): Any?

}