package practice.money.transfer

import java.math.BigDecimal

class LimitViolationException(
    override val message: String,
) : RuntimeException() {

    companion object {

        fun lowerLimitViolation(limit: BigDecimal? = BigDecimal.ZERO) =
            LimitViolationException("Violated lower limit which is $limit")

        fun upperLimitViolation(limit: BigDecimal? = BigDecimal.ZERO) =
            LimitViolationException("Violated upper limit which is $limit")

    }

}