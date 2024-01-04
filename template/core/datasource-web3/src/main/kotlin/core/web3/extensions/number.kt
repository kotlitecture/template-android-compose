package core.web3.extensions

import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger

fun BigDecimal.weiFromGwei(): BigInteger = Convert.toWei(this, Convert.Unit.GWEI).toBigInteger()
fun BigDecimal.weiFromEth(): BigInteger = Convert.toWei(this, Convert.Unit.ETHER).toBigInteger()

fun BigInteger.weiFromGwei(): BigInteger = toBigDecimal().weiFromGwei()
fun BigInteger.weiFromEth(): BigInteger = toBigDecimal().weiFromEth()
fun BigInteger.weiToGwei(): BigDecimal = Convert.fromWei(toString(), Convert.Unit.GWEI)
fun BigInteger.weiToEth(): BigDecimal = Convert.fromWei(toString(), Convert.Unit.ETHER)