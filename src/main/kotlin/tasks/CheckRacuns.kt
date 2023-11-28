package tasks

import database.Database
import database.racun.RacunDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import services.racun.RacunServices

@Component
class CheckRacuns(
    private val racunServices: RacunServices
) {

    @Scheduled(cron = "0 0/30 * * * *")
    fun runTask() {
        racunServices.checkRacuns()
        println("CheckRacuns runs every half hour")
    }

}