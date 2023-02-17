package practice.money.transfer.server

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import practice.money.transfer.exception.ForbiddenOperationException
import practice.money.transfer.exception.NoSuchAccountException

fun Application.configureErrorHandling() {
    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, ValidationFailResponse(cause.reasons))
        }

        exception<NoSuchAccountException> { call, _ ->
            call.respond(HttpStatusCode.NotFound, "Account not found.")
        }

        exception<ForbiddenOperationException> {call, cause ->
            call.respond(HttpStatusCode.Conflict, ForbiddenOperationResponse(cause.reason))
        }
    }
}
