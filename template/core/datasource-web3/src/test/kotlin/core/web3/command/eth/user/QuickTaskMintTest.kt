package core.web3.command.eth.user

import core.essentials.exception.DataException
import core.web3.AbstractTest
import core.web3.command.eth.contract.GetMintByHash
import core.web3.command.eth.quicktask.QuickTaskMint
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.tinylog.Logger
import java.math.BigDecimal
import kotlin.test.Ignore

class QuickTaskMintTest : AbstractTest() {

    @Test
    @Ignore
    fun `qt of proxy contract`() = performTest {
        val wallet = context.getEthWallet()
        val txHash = "0xfe8da67572c4419eb8c694d953a409b04ba2f488597bae4d5e51427348027a8d"
        val mint = GetMintByHash(txHash).execute(context)
        Assertions.assertTrue(mint.isMintable())
        val transaction = QuickTaskMint(mint, wallet).execute(context)
        Logger.debug("fee :: {}", transaction.getTransactionFee())
    }

    @Test
    fun `qt with insufficient funds`() = performTest {
        val txHashToCopy = "0xe88fd68ecce47b2151b340ed92bc02700af0eb930348abeeaa38e1a468522827"
        val mint = GetMintByHash(txHashToCopy).execute(context)
        val maxFee = BigDecimal("0.003")
        val wallet = context.getEthWallet()
        try {
            val transaction = QuickTaskMint(mint, wallet).execute(context)
            Assertions.assertFalse(transaction.isExecutable(maxFee))
            transaction.commit(maxFee)
            Assertions.fail<Unit>("no no no")
        } catch (e: Exception) {
            e as DataException
            Assertions.assertEquals("insufficient funds for transfer", e.message)
        }
    }

    @Test
    fun `qt with max mint exceed`() = performTest {
        val txHashToCopy = "0xfb70a144b8ff147ede3339b746fdd954fc0e6ee1b2757f3586bd38042a47dcfb"
        val mint = GetMintByHash(txHashToCopy).execute(context)
        val maxFee = BigDecimal("0.01")
        val wallet = ethMintWallet
        try {
            val transaction = QuickTaskMint(mint, wallet).execute(context)
            Assertions.assertFalse(transaction.isExecutable(maxFee))
            transaction.commit(maxFee)
            Assertions.fail<Unit>("no no no")
        } catch (e: Exception) {
            e as DataException
        }
    }

    @Test
    @Ignore
    fun `qt with mapped params`() = performTest {
        val txHashToCopy = "0x35971639d186f9a3131558f2e2b75a36b6b09531817f84fc608ee95d5530b03f"
        val mint = GetMintByHash(txHashToCopy).execute(context)
        val maxFee = BigDecimal("0.01")
        val wallet = context.getEthWallet()
        try {
            val transaction = QuickTaskMint(mint, wallet).execute(context)
            Assertions.assertTrue(transaction.isExecutable(maxFee))
//            transaction.commit(maxFee)
//            Assertions.fail<Unit>("no no no")
        } catch (e: Exception) {
            e as DataException
            Assertions.assertEquals("insufficient funds for transfer", e.message)
        }
    }

    @Test
    @Ignore
    fun `qt with changed params`() = performTest {
        val txHashToCopy = "0x82773150ded823347c107078b5e2001e090151bf1fcde074672c74cf82756606"
        val mint = GetMintByHash(txHashToCopy).execute(context)
        val wallet = context.getEthWallet()
        val transaction = QuickTaskMint(mint, wallet).execute(context)

    }

}