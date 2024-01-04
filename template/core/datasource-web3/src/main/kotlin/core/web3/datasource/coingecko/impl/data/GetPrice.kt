package core.web3.datasource.coingecko.impl.data

import java.math.BigDecimal

class GetPrice {

    class Response : HashMap<String, Prices>()

    class Prices : HashMap<String, BigDecimal>()
}