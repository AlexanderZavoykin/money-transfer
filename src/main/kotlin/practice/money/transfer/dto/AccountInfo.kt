package practice.money.transfer.dto

import kotlinx.serialization.Serializable
import practice.money.transfer.server.BigDecimalSerializer
import java.math.BigDecimal

@Serializable
data class AccountInfo(
    val id: String,
    @Serializable(with = BigDecimalSerializer::class)
    val balance: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val limit: BigDecimal? = null,
)
