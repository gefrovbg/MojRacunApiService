package endpoints.banks

import database.data.BankWithGroupedCurses
import database.data.BankWithListCurses
import database.data.Curs
import database.curs.CursDatabase
import endpoints.dto.ApiException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/banks")
class BanksController {

    @Autowired
    private val cursDatabase: CursDatabase? = null

    @GetMapping(value = ["/curs/latest"] ,produces = ["application/json"])
    fun getCursLatest(): List<Curs> {
        return try {
            cursDatabase?.getLatestCurs() ?: throw ApiException(404, "Not Found")
        }catch(e: Exception){
            throw ApiException(500, "${e.message}")
        }
    }

    @GetMapping(value = ["/curs/latest2week"] ,produces = ["application/json"])
    fun getCursLatest2Week(
        @RequestParam("bankName") bankName: String
    ): BankWithListCurses {
        return try {
            cursDatabase?.getLatestTwoWeekByBank(bankName) ?: throw ApiException(404, "Not Found")
        }catch(e: Exception){
            throw ApiException(500, "${e.message}")
        }
    }

    @GetMapping(value = ["/curs/latest2week/grouped"] ,produces = ["application/json"])
    fun getCursLatest2WeekGrouped(
        @RequestParam("bankName") bankName: String,
        @RequestParam("dollar") dollar: Boolean
    ): BankWithGroupedCurses.BankWithGroupedListCurses<out Any> {
        return try {
            if (dollar)
                cursDatabase?.getGroupedDollarLatestTwoWeekByBank(bankName) ?: throw ApiException(404, "Not Found")
            else
                cursDatabase?.getGroupedEuroLatestTwoWeekByBank(bankName) ?: throw ApiException(404, "Not Found")
        }catch(e: Exception){
            throw ApiException(500, "${e.message}")
        }
    }

}