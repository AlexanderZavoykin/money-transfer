package practice.money.transfer.config

import java.sql.Connection

data class DbConfig(
    val driverClassName: String,
    val dataSourceUser: String,
    val dataSourcePassword: String,
    val jdbcUrl: String,
    val maxLifetime: Int,
    val connectionTimeout: Int,
    val idleTimeout: Int,
    val maximumPoolSize: Int,
    val minimumIdle: Int,
    val poolName: String,
    val autoCommit: Boolean,
    val leakDetectionThreshold: Int,
    val isolationLevel: Int = Connection.TRANSACTION_READ_COMMITTED,
): IConfig
