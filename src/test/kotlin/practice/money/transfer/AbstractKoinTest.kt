package practice.money.transfer

import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.junit5.KoinTestExtension

abstract class AbstractKoinTest {

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            persistenceModule,
            serviceModule,
            serverModule,
        )
    }

}