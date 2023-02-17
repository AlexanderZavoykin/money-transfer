package practice.money.transfer.service

import java.math.BigDecimal

interface TransferService {

    fun deposit(accountId: String, amount: BigDecimal)

    fun withdraw(accountId: String, amount: BigDecimal)

    fun transfer(fromId: String, toId: String, amount: BigDecimal)

}