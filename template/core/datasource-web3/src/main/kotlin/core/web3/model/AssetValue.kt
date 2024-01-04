package core.web3.model

import java.math.BigDecimal

data class AssetValue(
    val address: String,
    val value: BigDecimal
)