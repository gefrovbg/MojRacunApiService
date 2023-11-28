import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@EnableAsync
@EnableScheduling
@ComponentScan(
    basePackages = [
        "database",
        "endpoints",
        "tasks",
        "tools",
        "ngrok",
        "banks",
        "security",
        "mailclient",
        "services"
    ]
)
@SpringBootApplication
class MojRacunApiService

fun main(args: Array<String>) {
    runApplication<MojRacunApiService>(*args)
}