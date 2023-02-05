package practice.money.transfer

import java.math.BigDecimal

data class AccountInfo(
    val id: String,
    val balance: BigDecimal,
    val limit: BigDecimal? = null,
)