package tools

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Config {

    @Value("\${app.database.url}")
    private lateinit var dbUrl: String

    fun getDatabaseUrl(): String{
        return dbUrl
    }

    @Value("\${app.database.driver}")
    private lateinit var dbDriver: String

    fun getDatabaseDriver(): String{
        return dbDriver
    }

    @Value("\${app.database.user}")
    private lateinit var dbUser: String

    fun getDatabaseUser(): String{
        return dbUser
    }

    @Value("\${app.database.password}")
    private lateinit var dbPassword: String

    fun getDatabasePassword(): String{
        return dbPassword
    }

    @Value("\${app.ngrok.ports}")
    private lateinit var ngrokPortsList: String

//    private val myArray: List<Int> = ngrokPortsList.split(",").map { it.trim().toInt() }

    fun getNgrokPortsList(): List<Int> {
        return ngrokPortsList.split(",").map { it.trim().toInt() }
    }

    @Value("\${ngrok.authToken}")
    private lateinit var ngrokAuth: String

    fun getNgrokAuth(): String{
        return ngrokAuth
    }

    @Value("\${server.port}")
    private lateinit var serverPort: String

    fun getServerPort(): Int{
        return serverPort.toInt()
    }

}