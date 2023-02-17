package practice.money.transfer.exception

class ForbiddenOperationException(
    val reason: String,
) : RuntimeException(reason)
