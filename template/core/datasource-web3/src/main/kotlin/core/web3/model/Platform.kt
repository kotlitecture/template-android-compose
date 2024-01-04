package core.web3.model

enum class Platform(val id: String, val title:String = id) {

    OpenSea("opensea"),

    X2Y2("x2y2"),

    Blur("blur"),

    LooksRare("looksrare"),

    UniSwapV2("uniswap_v2", title = "Uni V2"),

    UniSwapV3("uniswap_v3", title = "Uni V3"),

    Unknown("unknown");

    companion object {
        private val by = entries.associateBy { it.id }

        fun by(id: String?) = by[id] ?: Unknown
    }

}