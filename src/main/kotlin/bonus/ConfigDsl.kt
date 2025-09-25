package main.kotlin.bonus


data class Config(val dbConfig: DbConfig, val serverConfig: ServerConfig)
data class DbConfig(val url: String)
data class ServerConfig(val port: String)

@DslMarker
annotation class ConfigDsl

@ConfigDsl
class ServerConfigBuilder {
    var port: String = "8080"

    fun build(): ServerConfig {
        return ServerConfig(port)
    }
}

@ConfigDsl
class DbConfigBuilder {
    var url = ""

    fun build(): DbConfig {
        return DbConfig(url)
    }
}

@ConfigDsl
class ConfigBuilder {
    private var _dbConfig: DbConfig? = null
    private var _serverConfig: ServerConfig? = null

    fun db(block: DbConfigBuilder.() -> Unit) {
        _dbConfig = DbConfigBuilder().apply(block).build()
    }

    fun server(block: ServerConfigBuilder.() -> Unit) {
        _serverConfig = ServerConfigBuilder().apply(block).build()
    }

    fun build(): Config {
        val dbConfig = _dbConfig ?: throw Error("Missing database connection")
        val serverConfig = _serverConfig ?: throw Error("Missing server configuration")

        return Config(dbConfig, serverConfig)
    }
}

object ConfigCtx  {
    fun config(myConfig: ConfigBuilder.() -> Unit): Config {
        return ConfigBuilder().apply(myConfig).build()
    }
}
