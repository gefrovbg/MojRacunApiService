package database.racun

import database.data.racun.Racun
import database.data.racun.RacunState
import database.data.racun.response.RacunInfoResponse
import java.time.LocalDate

interface RacunDatabase {

    fun saveRacun(racun: Racun, userMail: String, status: String)

    fun checkRacun(racun: Racun, userMail: String): String?

    fun getInfoByDate(userMail: String, date: LocalDate): RacunInfoResponse?

    fun getInfoByMonth(userMail: String, date: LocalDate): RacunInfoResponse?

    fun getRacunsListForCheck(): Map<String,List<Racun>>

    fun updateRacunStatus(status: String, id: Long?)

}