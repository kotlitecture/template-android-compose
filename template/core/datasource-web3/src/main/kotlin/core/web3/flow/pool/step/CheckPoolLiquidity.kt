package core.web3.flow.pool.step

import core.essentials.flow.FlowStep
import core.web3.flow.pool.PoolFlowContext
import java.math.BigDecimal

class CheckPoolLiquidity : FlowStep<PoolFlowContext>() {

    override suspend fun doProceed(context: PoolFlowContext) {
        context.poolLiquidity = context.pool.totalValueLockedETH ?: BigDecimal.ZERO
    }

}