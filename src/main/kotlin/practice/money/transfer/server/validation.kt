package practice.money.transfer.server

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import practice.money.transfer.dto.AccountInfo
import java.math.BigDecimal

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<AccountInfo> {
            with(mutableListOf<String>()) {
                addIf("Account ID cannot be blank") { it.id.isBlank() }
                addIf("Amount should be greater or equals than zero") { it.balance < BigDecimal.ZERO }
                addIf("Limit should be greater or equals than zero") { it.limit != null && it.limit < BigDecimal.ZERO }
                addIf("Limit should be greater or equals than balance") { it.balance > it.limit }
                validationResult(this)
            }
        }

        validate<AccountUpdateRequest> {
            if (it.limit < BigDecimal.ZERO) ValidationResult.Invalid("Limit should be greater or equals than zero")
            else ValidationResult.Valid
        }

        validate<TransferRequest> {
            with(mutableListOf<String>()) {
                addIf("Cannot transfer to the same account") { it.fromAccountId == it.toAccountId }
                addIf("Account ID cannot be blank") { it.fromAccountId.isBlank() || it.toAccountId.isBlank() }
                addIf("Amount should be greater than zero") { it.amount <= BigDecimal.ZERO }
                validationResult(this)
            }
        }
    }
}

private fun <T> MutableList<T>.addIf(element: T, predicate: () -> Boolean) {
    if (predicate.invoke()) this.add(element)
}

private fun validationResult(reasons: List<String>): ValidationResult =
    if (reasons.isNotEmpty()) ValidationResult.Invalid(reasons)
    else ValidationResult.Valid
