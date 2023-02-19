package practice.money.transfer.service

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.koin.java.KoinJavaComponent
import org.koin.test.KoinTest
import practice.money.transfer.accountInfoOf
import practice.money.transfer.assertMathematicallyEquals
import practice.money.transfer.dto.AccountInfo
import practice.money.transfer.exception.ForbiddenOperationException
import practice.money.transfer.exception.NoSuchAccountException
import practice.money.transfer.randomString
import practice.money.transfer.startKoinTest
import java.math.BigDecimal
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AccountServiceTest : KoinTest {

    private val accountService: AccountService by KoinJavaComponent.inject(AccountService::class.java)

    @Test
    fun `create and get account successfully`() {
        val accountInfo = accountInfoOf(balance = 100.0, limit = 999.0)

        var resultInfo: AccountInfo? = null
        assertDoesNotThrow {
            accountService.add(accountInfo)
            resultInfo = accountService.get(accountInfo.id)
        }

        assertNotNull(resultInfo)
        assertMathematicallyEquals(accountInfo.balance, resultInfo!!.balance)
        assertMathematicallyEquals(accountInfo.limit!!, resultInfo!!.limit!!)
    }

    @Test
    fun `create account unsuccessfully - start balance is greater than limit`() {
        val accountInfo = accountInfoOf(balance = 100.0, limit = 0.0)

        assertThrows<IllegalArgumentException> {
            accountService.add(accountInfo)
        }

        assertThrows<NoSuchAccountException> {
            accountService.get(accountInfo.id)
        }
    }

    @Test
    fun `create account unsuccessfully - account already exists`() {
        val accountInfo = accountInfoOf(balance = 100.0, limit = 999.0)

        assertDoesNotThrow {
            accountService.add(accountInfo)
        }

        assertThrows<ForbiddenOperationException> {
            accountService.add(accountInfo)
        }
    }

    @Test
    fun `close account successfully`() {
        val accountInfo = accountInfoOf(balance = 100.0, limit = 999.0)

        assertDoesNotThrow {
            accountService.add(accountInfo)
            accountService.close(accountInfo.id)
        }

        assertThrows<NoSuchAccountException> {
            accountService.get(accountInfo.id)
        }
    }

    @Test
    fun `close account unsuccessfully - no such account`() {
        assertThrows<NoSuchAccountException> {
            accountService.close(randomString())
        }
    }

    @Test
    fun `update limit successfully`() {
        val accountInfo = accountInfoOf(balance = 100.0, limit = 999.0)

        val newLimit = BigDecimal.valueOf(1999.0)

        assertDoesNotThrow {
            accountService.add(accountInfo)
            accountService.updateLimit(accountInfo.id, newLimit)
        }

        assertMathematicallyEquals(newLimit, accountService.get(accountInfo.id).limit!!)
    }

    @Test
    fun `update limit unsuccessfully - no such account`() {
        assertThrows<NoSuchAccountException> {
            accountService.updateLimit(randomString(), BigDecimal.valueOf(1999.0))
        }
    }

    @Test
    fun `get all accounts successfully`() {
        assertDoesNotThrow {
            accountService.add(accountInfoOf(100.0, 999.0))
            val accounts = accountService.getAll()
            assertTrue { accounts.isNotEmpty() }
        }
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun start() {
            startKoinTest()
        }
    }

}