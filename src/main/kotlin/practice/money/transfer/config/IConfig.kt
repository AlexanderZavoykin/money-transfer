package practice.money.transfer.config

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource

interface IConfig {

    companion object {

        @PublishedApi
        internal const val resourceConfigName = "/application.yaml"

        inline fun <reified T : IConfig> load(configPath: String = resourceConfigName): T =
            ConfigLoaderBuilder
                .default()
                .addResourceSource(configPath)
                .build()
                .loadConfig<T>()
                .getUnsafe()

    }

}