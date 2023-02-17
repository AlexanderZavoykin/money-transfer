package practice.money.transfer.config

import com.zaxxer.hikari.HikariConfig
import java.util.*

fun DbConfig.toHikariConfig(): HikariConfig {
    val props = Properties().apply {
        put("driverClassName", driverClassName)
        put("dataSource.user", dataSourceUser)
        put("dataSource.password", dataSourcePassword)
        put("jdbcUrl", jdbcUrl)
        put("maxLifetime", maxLifetime)
        put("connectionTimeout", connectionTimeout)
        put("idleTimeout", idleTimeout)
        put("maximumPoolSize", maximumPoolSize)
        put("minimumIdle", minimumIdle)
        put("poolName", poolName)
        put("autoCommit", autoCommit)
        put("leakDetectionThreshold", leakDetectionThreshold)
    }
    return HikariConfig(props)
}