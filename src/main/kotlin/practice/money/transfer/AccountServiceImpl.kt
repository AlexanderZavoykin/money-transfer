package practice.money.transfer

import java.math.BigDecimal

class AccountServiceImpl(
    private val accountStore: AccountStore,
) : AccountService {

    override fun get(accountId: String): AccountInfo =
        accountStore.get(accountId)?.toInfo() ?: throw NoSuchAccountException.notFoundById(accountId)

    override fun getAll(): List<AccountInfo> = accountStore.getAll().map { it.toInfo() }

    override fun add(account: AccountInfo): Boolean {
        val acc = account.let { Account(it.id, it.balance, it.limit) }
        return accountStore.add(acc)
    }

    override fun close(accountId: String): AccountInfo =
        accountStore.remove(accountId)
            ?.toInfo()
            ?: throw NoSuchAccountException.notFoundById(accountId)

    override fun updateLimit(accountId: String, limit: BigDecimal): Boolean =
        accountStore.updateLimit(accountId = accountId, limit = limit)

    private fun Account.toInfo(): AccountInfo =
        AccountInfo(id = id, balance = balance, limit = limit)

}