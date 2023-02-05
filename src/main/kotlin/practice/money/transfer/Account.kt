package practice.money.transfer

import java.math.BigDecimal

class Account(
    val id: String,
    val balance: BigDecimal = BigDecimal.ZERO,
    val limit: BigDecimal? = null,
) {

    init {
        limit?.let {
            require(it >= balance) { "Limit should be greater then or equals start balance" }
        }
    }

}