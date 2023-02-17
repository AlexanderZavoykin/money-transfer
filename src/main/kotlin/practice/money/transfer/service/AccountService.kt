package practice.money.transfer.service

import practice.money.transfer.dto.AccountInfo
import java.math.BigDecimal

interface AccountService {

    fun get(accountId: String): AccountInfo

    fun getAll(): List<AccountInfo>

    fun add(account: AccountInfo): Boolean

    fun close(accountId: String): AccountInfo

    fun updateLimit(accountId: String, limit: BigDecimal): AccountInfo

}