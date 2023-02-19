package practice.money.transfer

import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.flywaydb.core.Flyway
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.ThreadLocalTransactionProvider
import org.koin.dsl.module
import practice.money.transfer.config.ApplicationConfig
import practice.money.transfer.config.DbConfig
import practice.money.transfer.config.IConfig
import practice.money.transfer.config.toFlywayConfig
import practice.money.transfer.config.toHikariConfig
import practice.money.transfer.dao.AccountDao
import practice.money.transfer.dao.JooqAccountDao
import practice.money.transfer.dao.TransactionManager
import practice.money.transfer.server.configureErrorHandling
import practice.money.transfer.server.configureRoutings
import practice.money.transfer.server.configureSerialization
import practice.money.transfer.server.configureValidation
import practice.money.transfer.service.AccountService
import practice.money.transfer.service.DbAccountService
import practice.money.transfer.service.DbTransferService
import practice.money.transfer.service.TransferService

val persistenceModule = module {
    val appConfig = IConfig.load<ApplicationConfig>()
    val dbConfig = appConfig.db

    single { dslContext(dbConfig) }
    single<AccountDao> { JooqAccountDao(get()) }
    single { TransactionManager(get()) }

    val flywayConfig = dbConfig.toFlywayConfig()
    single { Flyway(flywayConfig) }
}

val serviceModule = module {
    single<AccountService> { DbAccountService(get(), get()) }
    single<TransferService> { DbTransferService(get(), get()) }
}

val serverModule = module {
    val server = embeddedServer(Netty, 8080) {
        configureSerialization()
        configureValidation()
        configureErrorHandling()
        configureRoutings()
    }
    single { server }
}

private fun dslContext(dbConfig: DbConfig): DSLContext {
    val hikariConfig = dbConfig.toHikariConfig()
    val hikariDataSource = HikariDataSource(hikariConfig)

    val jooqConfig = DefaultConfiguration().apply {
        setDataSource(hikariDataSource)
        setSQLDialect(SQLDialect.POSTGRES)
        setTransactionProvider(ThreadLocalTransactionProvider(this.connectionProvider()))
    }

    return DSL.using(jooqConfig)
}
