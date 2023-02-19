package practice.money.transfer

import practice.money.transfer.dto.AccountInfo
import java.math.BigDecimal
import kotlin.random.Random
import kotlin.test.assertEquals

private val random = Random.Default

fun accountInfoOf(balance: Double, limit: Double): AccountInfo =
    AccountInfo(randomString(), BigDecimal.valueOf(balance), BigDecimal.valueOf(limit))

fun assertMathematicallyEquals(first: BigDecimal, second: BigDecimal) {
    assertEquals(0, first.compareTo(second))
}

fun randomString(): String = random.nextLong().toString()
