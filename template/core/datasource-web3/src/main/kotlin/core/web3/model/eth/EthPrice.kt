package core.web3.model.eth

import java.math.BigDecimal

data class EthPrice(
    val usdRate: BigDecimal,
    val btcRate: BigDecimal
)