package practice.money.transfer.dao

import practice.money.transfer.model.Account
import java.math.BigDecimal

interface AccountDao {

    fun get(accountId: String): Account?

    fun getAll(): List<Account>

    fun insert(account: Account): Int

    fun updateLimit(accountId: String, limit: BigDecimal): Account?

    fun delete(accountId: String): Account?

    fun updateBalanceByAmount(accountId: String, amount: BigDecimal): Int

}
