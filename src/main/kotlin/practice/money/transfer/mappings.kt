package practice.money.transfer

import practice.money.transfer.dto.AccountInfo
import practice.money.transfer.model.Account

fun Account.toInfo(): AccountInfo = AccountInfo(id = id, balance = balance, limit = limit)

fun AccountInfo.toAccount(): Account = Account(id = id, balance = balance, limit = limit)