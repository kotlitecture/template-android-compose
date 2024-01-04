package core.web3.flow.pool.step

import core.essentials.flow.FlowStep
import core.essentials.misc.extensions.equalsZero
import core.web3.flow.pool.PoolFlowContext

class CalculateScore : FlowStep<PoolFlowContext>() {

    override suspend fun doProceed(context: PoolFlowContext) {
        var score = 0

        if (context.contractIsVerified) {
            score += 5
        }
        if (context.contractIsExecutable) {
            score += 5
        }
        if (!context.contractIsIncorrect) {
            score += 5
        }
        if (context.contractIsRenounced) {
            score += 5
        }
        if (!context.contractIsSuspicious) {
            score += 5
        }
        if (context.twitterUrl != null) {
            score += 5
        } else {
            score -= 5
        }
        if (context.discordUrl != null) {
            score += 1
        }
        if (context.siteUrl != null) {
            score += 1
        }
        if (context.telegramUrl != null) {
            score += 1
        }
        if (context.poolBuySlippage != null && context.poolBuySlippage!! <= 20) {
            score += 5
        }
        if (!context.poolLiquidity.equalsZero() && context.poolLiquidity <= 5.toBigDecimal()) {
            score += 5
        }
        if (context.aimBotBuyPosition > 0 && context.aimBotSalePosition == 0) {
            score += 5
        }

        context.score = score
    }

}