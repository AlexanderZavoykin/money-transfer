package practice.money.transfer

import java.math.BigDecimal

interface TransferService {

    fun deposit(accountId: String, amount: BigDecimal): AccountInfo

    fun withdraw(accountId: String, amount: BigDecimal): AccountInfo

    fun transfer(fromId: String, toId: String, amount: BigDecimal): Boolean

}