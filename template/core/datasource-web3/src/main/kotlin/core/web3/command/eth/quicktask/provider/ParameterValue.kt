package core.web3.command.eth.quicktask.provider

import core.web3.model.eth.Abi

data class ParameterValue(
    val parameter: Abi.Parameter,
    val value: Any?
)