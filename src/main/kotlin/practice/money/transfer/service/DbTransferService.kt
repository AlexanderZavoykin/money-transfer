package practice.money.transfer.service

import practice.money.transfer.dao.AccountDao
import practice.money.transfer.dao.TransactionManager
import java.math.BigDecimal

class DbTransferService(
    private val dao: AccountDao,
    private val txManager: TransactionManager,
) : TransferService {

    override fun deposit(accountId: String, amount: BigDecimal) {
        checkAmount(amount)
        txManager.transaction {
            updateBalance(accountId, amount)
        }
    }

    override fun withdraw(accountId: String, amount: BigDecimal) {
        checkAmount(amount)
        txManager.transaction {
            updateBalance(accountId, amount.negate())
        }
    }

    override fun transfer(fromId: String, toId: String, amount: BigDecimal) {
        checkAmount(amount)
        txManager.transaction {
            updateBalance(fromId, amount.negate())
            updateBalance(toId, amount)
        }
    }

    private fun checkAmount(amount: BigDecimal) {
        require(amount > BigDecimal.ZERO) { "Amount should be greater than zero!" }
    }

    private fun updateBalance(accountId: String, amount: BigDecimal) {
        dao.updateBalanceByAmount(accountId, amount)
    }


}