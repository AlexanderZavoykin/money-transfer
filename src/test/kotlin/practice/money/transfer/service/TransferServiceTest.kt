package practice.money.transfer.service

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.KoinTest
import practice.money.transfer.accountInfoOf
import practice.money.transfer.assertMathematicallyEquals
import practice.money.transfer.exception.ForbiddenOperationException
import practice.money.transfer.startKoinTest
import java.math.BigDecimal

class TransferServiceTest : KoinTest {

    private val accountService: AccountService by inject(AccountService::class.java)
    private val transferService: TransferService by inject(TransferService::class.java)

    @Test
    fun `deposit money successfully`() {
        val accountInfo = accountInfoOf(balance = 100.0, limit = 999.0)
        accountService.add(accountInfo)

        val amount = BigDecimal.valueOf(500.0)

        assertDoesNotThrow {
            transferService.deposit(accountInfo.id, amount)
        }

        assertMathematicallyEquals(accountInfo.balance + amount, accountService.get(accountInfo.id).balance)
    }

    @Test
    fun `deposit money unsuccessfully - limit violation`() {
        val accountInfo = accountInfoOf(balance = 100.0, limit = 999.0)
        accountService.add(accountInfo)

        val amount = BigDecimal.valueOf(999.0)

        assertThrows<ForbiddenOperationException> {
            transferService.deposit(accountInfo.id, amount)
        }

        assertMathematicallyEquals(accountInfo.balance, accountService.get(accountInfo.id).balance)
    }

    @Test
    fun `withdraw money successfully`() {
        val accountInfo = accountInfoOf(balance = 100.0, limit = 999.0)
        accountService.add(accountInfo)

        val amount = BigDecimal.valueOf(50.0)

        assertDoesNotThrow {
            transferService.withdraw(accountInfo.id, amount)
        }

        assertMathematicallyEquals(accountInfo.balance - amount, accountService.get(accountInfo.id).balance)
    }

    @Test
    fun `withdraw money unsuccessfully - not enough money`() {
        val accountInfo = accountInfoOf(balance = 100.0, limit = 999.0)
        accountService.add(accountInfo)

        val amount = BigDecimal.valueOf(999.0)

        assertThrows<ForbiddenOperationException> {
            transferService.withdraw(accountInfo.id, amount)
        }

        assertMathematicallyEquals(accountInfo.balance, accountService.get(accountInfo.id).balance)
    }


    @Test
    fun `transfer money successfully`() {
        val accountInfo1 = accountInfoOf(balance = 100.0, limit = 999.0)
        val accountInfo2 = accountInfoOf(balance = 100.0, limit = 999.0)

        accountService.add(accountInfo1)
        accountService.add(accountInfo2)

        val amount = BigDecimal.valueOf(50.0)

        assertDoesNotThrow {
            transferService.transfer(accountInfo1.id, accountInfo2.id, amount)
        }

        assertMathematicallyEquals(accountInfo1.balance - amount, accountService.get(accountInfo1.id).balance)
        assertMathematicallyEquals(accountInfo2.balance + amount, accountService.get(accountInfo2.id).balance)
    }

    @Test
    fun `transfer money unsuccessfully - not enough money`() {
        val accountInfo1 = accountInfoOf(balance = 100.0, limit = 999.0)
        val accountInfo2 = accountInfoOf(balance = 100.0, limit = 999.0)

        accountService.add(accountInfo1)
        accountService.add(accountInfo2)

        val amount = BigDecimal.valueOf(200.0)

        assertThrows<ForbiddenOperationException> {
            transferService.transfer(accountInfo1.id, accountInfo2.id, amount)
        }

        assertMathematicallyEquals(accountInfo1.balance, accountService.get(accountInfo1.id).balance)
        assertMathematicallyEquals(accountInfo2.balance, accountService.get(accountInfo2.id).balance)
    }

    @Test
    fun `transfer money unsuccessfully - second account limit violation`() {
        val accountInfo1 = accountInfoOf(balance = 100.0, limit = 999.0)
        val accountInfo2 = accountInfoOf(balance = 100.0, limit = 101.0)

        accountService.add(accountInfo1)
        accountService.add(accountInfo2)

        val amount = BigDecimal.valueOf(50.0)

        assertThrows<ForbiddenOperationException> {
            transferService.transfer(accountInfo1.id, accountInfo2.id, amount)
        }

        assertMathematicallyEquals(accountInfo1.balance, accountService.get(accountInfo1.id).balance)
        assertMathematicallyEquals(accountInfo2.balance, accountService.get(accountInfo2.id).balance)
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun startKoin() {
            startKoinTest()
        }
    }

}