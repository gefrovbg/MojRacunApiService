package database.data.racun.response

import database.data.racun.Racun
import java.time.LocalDate

data class RacunInfoResponse(
    val amount: Double,
    val date: LocalDate,
    val racunList: List<Racun>
)