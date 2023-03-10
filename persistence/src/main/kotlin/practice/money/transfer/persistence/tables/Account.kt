/*
 * This file is generated by jOOQ.
 */
package practice.money.transfer.persistence.tables


import java.math.BigDecimal
import java.util.function.Function

import kotlin.collections.List

import org.jooq.Check
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row3
import org.jooq.Schema
import org.jooq.SelectField
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl

import practice.money.transfer.persistence.Public
import practice.money.transfer.persistence.keys.ACCOUNT_PKEY
import practice.money.transfer.persistence.tables.records.AccountRecord


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Account(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, AccountRecord>?,
    aliased: Table<AccountRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<AccountRecord>(
    alias,
    Public.PUBLIC,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>public.account</code>
         */
        val ACCOUNT: Account = Account()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<AccountRecord> = AccountRecord::class.java

    /**
     * The column <code>public.account.id</code>.
     */
    val ID: TableField<AccountRecord, String?> = createField(DSL.name("id"), SQLDataType.VARCHAR(36).nullable(false).defaultValue(DSL.field("(uuid_generate_v4())::character varying", SQLDataType.VARCHAR)), this, "")

    /**
     * The column <code>public.account.balance</code>.
     */
    val BALANCE: TableField<AccountRecord, BigDecimal?> = createField(DSL.name("balance"), SQLDataType.NUMERIC(16, 2).nullable(false).defaultValue(DSL.field("0", SQLDataType.NUMERIC)), this, "")

    /**
     * The column <code>public.account.limit</code>.
     */
    val LIMIT: TableField<AccountRecord, BigDecimal?> = createField(DSL.name("limit"), SQLDataType.NUMERIC(16, 2).nullable(false).defaultValue(DSL.field("0", SQLDataType.NUMERIC)), this, "")

    private constructor(alias: Name, aliased: Table<AccountRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<AccountRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>public.account</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.account</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.account</code> table reference
     */
    constructor(): this(DSL.name("account"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, AccountRecord>): this(Internal.createPathAlias(child, key), child, key, ACCOUNT, null)
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getPrimaryKey(): UniqueKey<AccountRecord> = ACCOUNT_PKEY
    override fun getChecks(): List<Check<AccountRecord>> = listOf(
        Internal.createCheck(this, DSL.name("account_balance_check"), "((balance >= (0)::numeric))", true),
        Internal.createCheck(this, DSL.name("account_check"), "((\"limit\" >= balance))", true)
    )
    override fun `as`(alias: String): Account = Account(DSL.name(alias), this)
    override fun `as`(alias: Name): Account = Account(alias, this)
    override fun `as`(alias: Table<*>): Account = Account(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Account = Account(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Account = Account(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Account = Account(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row3<String?, BigDecimal?, BigDecimal?> = super.fieldsRow() as Row3<String?, BigDecimal?, BigDecimal?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (String?, BigDecimal?, BigDecimal?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (String?, BigDecimal?, BigDecimal?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
