package practice.money.transfer

import java.math.BigDecimal

class TransferServiceImpl(
    private val accountStore: AccountStore,
) : TransferService {

    override fun deposit(accountId: String, amount: BigDecimal): AccountInfo {
        synchronized(accountId.intern()) {
            return doDeposit(accountId, amount)
        }
    }

    override fun withdraw(accountId: String, amount: BigDecimal): AccountInfo =
        synchronized(accountId.intern()) {
            return doWithdraw(accountId, amount)
        }

    override fun transfer(fromId: String, toId: String, amount: BigDecimal): Boolean {
        if (fromId == toId) {
            throw ForbiddenOperationException("Can not transfer from an account to itself!")
        }

        val (firstKey, secondKey) = if (fromId > toId) (fromId to toId) else (toId to fromId)

        synchronized(firstKey.intern()) {
            synchronized(secondKey.intern()) {
                doWithdraw(fromId, amount)
                doDeposit(toId, amount)
            }
        }

        return true
    }

    private fun doDeposit(accountId: String, amount: BigDecimal): AccountInfo {
        val account = accountStore.get(accountId)
            ?: throw NoSuchAccountException.notFoundById(accountId)

        val newBalance = account.balance + amount

        val finalBalance = if (accountStore.updateBalance(accountId, newBalance)) {
            newBalance
        } else {
            account.balance
        }

        return AccountInfo(accountId, finalBalance, account.limit)
    }

    private fun doWithdraw(accountId: String, amount: BigDecimal): AccountInfo {
        val account = accountStore.get(accountId)
            ?: throw NoSuchAccountException.notFoundById(accountId)

        val newBalance = account.balance - amount

        val finalBalance = if (accountStore.updateBalance(accountId, newBalance)) {
            newBalance
        } else {
            account.balance
        }

        return AccountInfo(accountId, finalBalance, account.limit)
    }

}