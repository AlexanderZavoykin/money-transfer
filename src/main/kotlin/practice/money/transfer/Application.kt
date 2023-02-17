package practice.money.transfer

import io.ktor.server.netty.*
import org.flywaydb.core.Flyway
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

object Application {

    @JvmStatic
    fun main(args : Array<String>) {
        startKoin {
            modules(
                persistenceModule,
                serviceModule,
                serverModule,
            )
        }

        val flyway: Flyway by inject(Flyway::class.java)
        flyway.migrate()

        val server: NettyApplicationEngine by inject(NettyApplicationEngine::class.java)
        server.start(wait = true)
    }

}
