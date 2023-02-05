package practice.money.transfer

import java.math.BigDecimal

interface AccountStore {

    fun get(accountId: String): Account?

    fun getAll(): List<Account>

    fun add(account: Account): Boolean

    fun updateBalance(accountId: String, balance: BigDecimal): Boolean

    fun updateLimit(accountId: String, limit: BigDecimal): Boolean

    fun remove(accountId: String): Account?

}