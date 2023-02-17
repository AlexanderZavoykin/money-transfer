package practice.money.transfer.server

import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class TransferRequest(
    val fromAccountId: String,
    val toAccountId: String,
    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal,
)

@Serializable
data class TransactionRequest(
    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal,
)

@Serializable
data class AccountUpdateRequest(
    @Serializable(with = BigDecimalSerializer::class)
    val limit: BigDecimal,
)

@Serializable
data class ValidationFailResponse(
    val reasons: List<String>,
)

@Serializable
data class ForbiddenOperationResponse(
    val reason: String,
)
