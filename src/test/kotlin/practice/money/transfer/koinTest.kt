package practice.money.transfer

import org.flywaydb.core.Flyway
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent

fun startKoinTest() {
    startKoin {
        modules(
            persistenceModule,
            serviceModule,
        )
    }

    val flyway: Flyway by KoinJavaComponent.inject(Flyway::class.java)
    flyway.migrate()
}