package core.web3.flow.pool

import core.essentials.flow.Flow
import core.essentials.flow.ParallelGateway
import core.web3.flow.pool.step.CalculateScore
import core.web3.flow.pool.step.CheckAimBotActivity
import core.web3.flow.pool.step.CheckBuyCommission
import core.web3.flow.pool.step.CheckContractRenounced
import core.web3.flow.pool.step.CheckContractVerified
import core.web3.flow.pool.step.CheckPoolLiquidity
import core.web3.flow.pool.step.CheckPoolNamedCorrectly
import core.web3.flow.pool.step.CheckPoolSuspicious
import core.web3.flow.pool.step.FetchPoolTransactions
import core.web3.flow.pool.step.CheckSocialMedia
import core.web3.flow.pool.step.FetchTokenStep

object PoolFlowRunner {

    suspend fun rate(context: PoolFlowContext) {
        val flow = Flow(
            "rate",
            FetchTokenStep(),
            ParallelGateway(
                FetchPoolTransactions(),
                CheckPoolNamedCorrectly(),
                CheckContractRenounced(),
                CheckContractVerified(),
                CheckPoolLiquidity(),
                CheckBuyCommission()
            ),
            ParallelGateway(
                CheckSocialMedia(),
                CheckPoolSuspicious(),
                CheckAimBotActivity()
            ),
            CalculateScore()
        )
        flow.proceed(context)
    }

}