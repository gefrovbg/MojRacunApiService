package database

import org.springframework.stereotype.Service
import tools.Config

@Service
class DatabaseImpl(
    private val config: Config
): Database {

    override fun connection() {
        org.jetbrains.exposed.sql.Database.connect(
            url = config.getDatabaseUrl(),
            driver = config.getDatabaseDriver(),
            user = config.getDatabaseUser(),
            password = config.getDatabasePassword()
        )
    }

}