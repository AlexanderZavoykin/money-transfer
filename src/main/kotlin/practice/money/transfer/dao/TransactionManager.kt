package practice.money.transfer.dao

import org.jooq.DSLContext
import org.jooq.TransactionalCallable
import org.jooq.exception.DataAccessException
import org.jooq.exception.SQLStateClass
import practice.money.transfer.exception.ForbiddenOperationException

class TransactionManager(
    private val dslContext: DSLContext,
) {

    fun <T> transaction(operation: TransactionalCallable<T>): T =
        try {
            dslContext.transactionResult(operation)
        } catch (e: DataAccessException) {
            val reason = when (e.sqlStateClass()) {
                SQLStateClass.C23_INTEGRITY_CONSTRAINT_VIOLATION -> "Limit violation"
                else -> "Forbidden operation"
            }

            throw ForbiddenOperationException(reason)
        }


}