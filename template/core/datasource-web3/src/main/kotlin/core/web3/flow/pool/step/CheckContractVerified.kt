package core.web3.flow.pool.step

import core.essentials.flow.FlowStep
import core.web3.flow.pool.PoolFlowContext
import org.tinylog.Logger

class CheckContractVerified : FlowStep<PoolFlowContext>() {

    override suspend fun doProceed(context: PoolFlowContext) {
        val api = context.web3.getEthereumInteractor()
        val wallet = context.wallet
        val contract = api.contractErc20(wallet, context.pool.token0.id)
        val source = contract.getSource()
        Logger.debug("source :: {}", source?.sourceCode)
        context.contractSourceCode = source?.sourceCode
        context.contractIsVerified = source != null
    }

}