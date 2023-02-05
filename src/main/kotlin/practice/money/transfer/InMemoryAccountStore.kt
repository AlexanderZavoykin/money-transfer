package practice.money.transfer

import java.math.BigDecimal
import java.util.concurrent.ConcurrentHashMap

class InMemoryAccountStore : AccountStore {

    private val accounts: MutableMap<String, Account> = ConcurrentHashMap()

    override fun get(accountId: String): Account? = accounts[accountId]

    override fun getAll(): List<Account> = accounts.values.toList()

    override fun add(account: Account): Boolean = accounts.putIfAbsent(account.id, account) == null

    override fun updateBalance(accountId: String, balance: BigDecimal): Boolean {
        val newAccount = accounts.computeIfPresent(accountId) { id, existingAccount ->
            if (balance > existingAccount.limit) {
                throw LimitViolationException.upperLimitViolation(existingAccount.limit)
            } else if (balance < BigDecimal.ZERO) {
                throw LimitViolationException.lowerLimitViolation()
            }

            Account(id, balance, existingAccount.limit)
        }

        return newAccount != null
    }

    override fun updateLimit(accountId: String, limit: BigDecimal): Boolean {
        val newAccount = accounts.computeIfPresent(accountId) { id, existingAccount ->
            if (existingAccount.balance > limit) {
                throw LimitViolationException.upperLimitViolation(existingAccount.limit)
            }

            Account(id, existingAccount.balance, limit)
        }

        return newAccount != null
    }

    override fun remove(accountId: String): Account? = accounts.remove(accountId)

}