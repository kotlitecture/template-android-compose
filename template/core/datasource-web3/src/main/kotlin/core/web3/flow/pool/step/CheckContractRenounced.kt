package core.web3.flow.pool.step

import core.essentials.flow.FlowStep
import core.web3.flow.pool.PoolFlowContext
import core.web3.model.Wallet

class CheckContractRenounced : FlowStep<PoolFlowContext>() {

    override suspend fun doProceed(context: PoolFlowContext) {
        val api = context.web3.getEthereumInteractor()
        val wallet = context.wallet
        val contract = api.contractErc20(wallet, context.pool.token0.id)
        context.contractIsRenounced = contract.owner() == Wallet.NULL_ADDRESS
    }

}