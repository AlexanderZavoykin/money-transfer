package practice.money.transfer.server

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent
import practice.money.transfer.dto.AccountInfo
import practice.money.transfer.service.AccountService
import practice.money.transfer.service.TransferService

val accountService: AccountService by KoinJavaComponent.inject(AccountService::class.java)
val transferService: TransferService by KoinJavaComponent.inject(TransferService::class.java)

fun Application.configureRoutings() {

    routing {
        route("/account") {
            get("/{accountId}") {
                val accountId = call.parameters["accountId"]!!
                call.respond(HttpStatusCode.OK, accountService.get(accountId))
            }

            get {
                call.respond(HttpStatusCode.OK, accountService.getAll())
            }

            post {
                val accountInfo = call.receive<AccountInfo>()
                if (accountService.add(accountInfo)) {
                    call.respond(HttpStatusCode.OK, accountInfo)
                } else {
                    call.respond(HttpStatusCode.Conflict, "Forbidden operation")
                }
            }

            delete {
                call.parameters["accountId"]
                    ?.let { call.respond(HttpStatusCode.OK, accountService.close(it)) }
            }

            patch("/{accountId}") {
                val accountId = call.parameters["accountId"]!!
                val request = call.receive<AccountUpdateRequest>()
                call.respond(HttpStatusCode.OK, accountService.updateLimit(accountId, request.limit))
            }

            post("/{accountId}/deposit") {
                val accountId = call.parameters["accountId"]!!
                val request = call.receive<TransactionRequest>()
                transferService.deposit(accountId, request.amount)
                call.respond(HttpStatusCode.OK)
            }

            post("/{accountId}/withdraw") {
                val accountId = call.parameters["accountId"]!!
                val request = call.receive<TransactionRequest>()
                transferService.withdraw(accountId, request.amount)
                call.respond(HttpStatusCode.OK)
            }
        }

        route("/transfer") {
            post {
                val request = call.receive<TransferRequest>()
                transferService.transfer(request.fromAccountId, request.toAccountId, request.amount)
                call.respond(HttpStatusCode.OK)
            }
        }
    }

}