package practice.money.transfer.server

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import practice.money.transfer.dto.AccountInfo
import java.math.BigDecimal

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<AccountInfo> {
            val reasons = mutableListOf<String>()

            if (it.id.isBlank()) {
                reasons.add("Account ID cannot be blank")
            }
            if (it.balance < BigDecimal.ZERO) {
                reasons.add("Amount should be greater or equals than zero")
            }
            if (it.limit != null && it.limit < BigDecimal.ZERO) {
                reasons.add("Limit should be greater or equals than zero")
            }
            if (it.balance > it.limit) {
                reasons.add("Limit should be greater or equals than balance")
            }

            if (reasons.isNotEmpty()) {
                ValidationResult.Invalid(reasons)
            } else {
                ValidationResult.Valid
            }
        }

        validate<AccountUpdateRequest> {
            if (it.limit < BigDecimal.ZERO) {
                ValidationResult.Invalid("Limit should be greater or equals than zero")
            } else {
                ValidationResult.Valid
            }
        }
    }
}
