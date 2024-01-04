package core.web3.datasource.uniswap.model

enum class Direction(val order:String, val prefix:String) {

    Latest("desc", "last"),

    Earliest("asc", "first"),

}