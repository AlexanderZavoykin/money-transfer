package practice.money.transfer.service

import practice.money.transfer.dao.AccountDao
import practice.money.transfer.dao.TransactionManager
import practice.money.transfer.dto.AccountInfo
import practice.money.transfer.exception.NoSuchAccountException
import practice.money.transfer.toAccount
import practice.money.transfer.toInfo
import java.math.BigDecimal

class DbAccountService(
    private val dao: AccountDao,
    private val txManager: TransactionManager,
) : AccountService {

    override fun get(accountId: String): AccountInfo =
        dao.get(accountId)
            ?.toInfo()
            ?: throw NoSuchAccountException.notFoundById(accountId)

    override fun getAll(): List<AccountInfo> = dao.getAll().map { it.toInfo() }

    override fun add(account: AccountInfo): Boolean =
        txManager.transaction { dao.insert(account.toAccount()) > 0 }

    override fun close(accountId: String): AccountInfo =
        txManager
            .transaction { dao.delete(accountId) }
            ?.toInfo()
            ?: throw NoSuchAccountException.notFoundById(accountId)

    override fun updateLimit(accountId: String, limit: BigDecimal): AccountInfo =
        txManager
            .transaction { dao.updateLimit(accountId, limit) }
            ?.toInfo()
            ?: throw NoSuchAccountException.notFoundById(accountId)

}
