package practice.money.transfer.dao

import org.jooq.DSLContext
import practice.money.transfer.model.Account
import practice.money.transfer.persistence.tables.references.ACCOUNT
import java.math.BigDecimal

class JooqAccountDao(
    private val dslContext: DSLContext,
): AccountDao {

    override fun get(accountId: String): Account? =
        dslContext
            .select(ACCOUNT.ID, ACCOUNT.BALANCE, ACCOUNT.LIMIT)
            .from(ACCOUNT)
            .where(ACCOUNT.ID.eq(accountId))
            .fetchOneInto(Account::class.java)

    override fun getAll(): List<Account> =
        dslContext
            .select(ACCOUNT.ID, ACCOUNT.BALANCE, ACCOUNT.LIMIT)
            .from(ACCOUNT)
            .fetchInto(Account::class.java)

    override  fun insert(account: Account): Int =
        dslContext
            .insertInto(ACCOUNT, ACCOUNT.ID, ACCOUNT.BALANCE, ACCOUNT.LIMIT)
            .values(account.id, account.balance, account.limit)
            .execute()

    override fun updateLimit(accountId: String, limit: BigDecimal): Account? =
        dslContext
            .update(ACCOUNT)
            .set(ACCOUNT.LIMIT, limit)
            .where(ACCOUNT.ID.eq(accountId))
            .returning()
            .fetchOneInto(Account::class.java)

    override fun delete(accountId: String): Account? =
        dslContext
            .deleteFrom(ACCOUNT)
            .where(ACCOUNT.ID.eq(accountId))
            .returning()
            .fetchOneInto(Account::class.java)

    override fun updateBalanceByAmount(accountId: String, amount: BigDecimal): Int =
        dslContext
            .update(ACCOUNT)
            .set(ACCOUNT.BALANCE, ACCOUNT.BALANCE + amount)
            .where(ACCOUNT.ID.eq(accountId))
            .execute()

}