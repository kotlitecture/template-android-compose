package core.web3.model

enum class AssetType(val id: String) {

    ERC20("erc20"),

    ERC721("erc721"),

    ERC1155("erc1155"),

    NotSupported("not_supported"),

    NotContract("not_contract"),

    Unknown("unknown");

    companion object {
        private val by = values().associateBy { it.id }

        fun by(id: String?) = by[id?.lowercase()] ?: Unknown
    }

}