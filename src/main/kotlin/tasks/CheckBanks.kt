package tasks

import banks.api.retrofit.banks.BankCursService
import database.data.Banks
import database.data.Curses
import database.Database
import database.data.Users
import database.data.racun.PaymentMethods
import database.data.racun.RacunItems
import database.data.racun.Racuns
import database.data.racun.Sellers
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


@Component
class CheckBanks {

    @Autowired
    private val database: Database? = null

    @PostConstruct
    fun init() {
        database?.connection()
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(
                Banks,
                Curses,
                Users,
                Racuns,
                RacunItems,
                PaymentMethods,
                Sellers
            )
        }
//        runTask()
    }

    @Autowired
    private val bankCursService: BankCursService? = null


    @Scheduled(cron = "0 0 * * * *")
    fun runTask() {
        bankCursService?.invoke()
        // Your task logic here
        println("This task runs every hour.")
    }

    @Scheduled(cron = "0 30 17 * * ?")
    fun sendPrvaNotification(){
        println("sendPrvaNotification")
    }
}