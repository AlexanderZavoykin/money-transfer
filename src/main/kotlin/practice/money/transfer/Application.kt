package practice.money.transfer

import io.ktor.server.netty.*
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

class Application

fun main() {
    startKoin { modules(persistenceModule, serviceModule, serverModule) }
    val server: NettyApplicationEngine by inject(NettyApplicationEngine::class.java)
    server.start(wait = true)
}
