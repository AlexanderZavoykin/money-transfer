package practice.money.transfer

class NoSuchAccountException private constructor(
    message: String
) : RuntimeException(message) {

    companion object {

        fun notFoundById(accountId: String): NoSuchAccountException = NoSuchAccountException("Account $accountId not found")

    }

}