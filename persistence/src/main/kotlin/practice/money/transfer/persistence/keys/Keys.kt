/*
 * This file is generated by jOOQ.
 */
package practice.money.transfer.persistence.keys


import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal

import practice.money.transfer.persistence.tables.Account
import practice.money.transfer.persistence.tables.records.AccountRecord



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val ACCOUNT_PKEY: UniqueKey<AccountRecord> = Internal.createUniqueKey(Account.ACCOUNT, DSL.name("account_pkey"), arrayOf(Account.ACCOUNT.ID), true)