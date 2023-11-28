package banks.api.retrofit.banks

import banks.Url
import banks.api.retrofit.banks.ckb.RestApiServiceCKB
import banks.api.retrofit.banks.nlb.RestApiServiceNLB
import banks.api.retrofit.banks.ziraat.RestApiServiceZiraat
import database.curs.CursDatabase
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tools.printCustom
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class BankCursService {

    @Autowired
    private val database: CursDatabase? = null

    @Autowired
    private val restApiServiceCKB: RestApiServiceCKB? = null

    @Autowired
    private val restApiServiceNLB: RestApiServiceNLB? = null

    @Autowired
    private val restApiServiceZiraat: RestApiServiceZiraat? = null

    private fun ckb(){
        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd")
        val dateInString = date.format(formatter)

        restApiServiceCKB?.getCurs(id = 1, datumOd = dateInString, datumDo = dateInString){ response ->
            if(response != null){
                restApiServiceCKB.getCurs(id = 3, datumOd = dateInString, datumDo = dateInString){ response1 ->
                    if(response1 != null){
                        printCustom("CKB: $response")
                        printCustom("CKB: $response1")
                        response.currency.forEach {
                            try {
                                if (it.optionDisplay == "USD"){
                                    val euro = try {
                                        response1.currency.first{ cur ->
                                            cur.optionDisplay == it.optionDisplay
                                        }.optionValue.toDouble()
                                    }catch (e: Exception){
                                        printCustom("CKB: ${e.message}")
                                        0.0
                                    }
                                    val usd = try {
                                        it.optionValue.toDouble()
                                    }catch (e: Exception){
                                        printCustom("CKB: ${e.message}")
                                        0.0
                                    }
                                    database?.saveCurs(
                                        bank = "ckb",
                                        bankLabel = "CKB",
                                        euro = if(euro == 0.0) 0.0 else 1/euro,
                                        usd = if (usd == 0.0) 0.0 else 1/usd
                                    )
                                }
                            }catch (e: Exception){
                                printCustom("CKB: ${e.message}")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun erste(){
        try {
            val doc = Jsoup.connect(Url.ersteUrl).get()
            doc.body().children().forEach {
                it.select("tbody").first().let { tbody ->
                    tbody?.select("tr")?.forEach { tr ->
                        val tdItem = tr.select("td")
                        if(tdItem.size >= 5) {
                            if (tdItem[1].text() == "USD") {
                                database?.saveCurs(
                                    bank = "erste",
                                    bankLabel = "Erste",
                                    euro = tdItem[5].text().replace(',', '.').toDouble(),
                                    usd = tdItem[3].text().replace(',', '.').toDouble()
                                )
                            }
                        }
                    }
                }
            }
        }catch (e: Exception){
            printCustom("Erste: ${e.message}")
        }
    }

    private fun hipotekarna(){
        try {
            val doc = Jsoup.connect(Url.hbUrl).get()
            doc.body().children().forEach {
                it.select("tbody").first().let { tbody ->
                    tbody?.select("tr")?.forEach { tr ->
                        val tdItem = tr.select("td")
                        if(tdItem.size > 3) {
                            if (tdItem[0].text() == "USD") {
                                database?.saveCurs(
                                    bank = "hipotekarna",
                                    bankLabel = "Hipotekarna",
                                    euro = 1 / tdItem[1].text().toDouble(),
                                    usd = 1 / tdItem[3].text().toDouble()
                                )
                            }
                        }
                    }
                }
            }
        }catch (e: Exception){
            printCustom("Hip: ${e.message}")
        }
    }

    private fun lovcen(){
        try {
            val docLov = Jsoup.connect(Url.lovcenUrl).get()
            docLov.body().children().forEach {
                it.select("tbody").first().let { tbody ->
                    tbody?.select("tr")?.forEach { tr ->
                        val tdItem = tr.select("td")
                        if (tdItem[0].text() == "USD") {
                            if (tdItem.size > 6) {
                                database?.saveCurs(
                                    bank = "lovcen",
                                    bankLabel = "Lovcen",
                                    euro = 1 / tdItem[6].text().toDouble(),
                                    usd = 1 / tdItem[2].text().toDouble()
                                )
                            }
                        }
                    }
                }
            }

        }catch (e: Exception){
            printCustom("Lovcen: ${e.message}")
        }
    }

    private fun nlb(){
        restApiServiceNLB?.getCurs(date = "latest"){ response ->
            if (response != null) {
                try {
                    val dollar = response.data.currencies.data.first() {
                        it.code == "USD"
                    }
                    database?.saveCurs(
                        bank = "nlb",
                        bankLabel = "NLB",
                        euro = 1/dollar.effective_sell,
                        usd = 1/dollar.effective_buy
                    )
                }catch (e: Exception){
                    printCustom("NLB: ${e.message}")
                }
            }else{
                printCustom("NLB: null")
            }
        }
    }

    private fun prva(){
        try {
            val docPrva = Jsoup.connect(Url.prvaUrl).get()

            docPrva.body().children().forEach {
                it.select("#modalKursnaLista > div > div > div.col-sm-12 > div > table").first().let { tbody ->
                    tbody?.select("tr")?.forEach { tr ->
                        val tdItem = tr.select("td")
                        if (tdItem.size > 3){
                            if (tdItem[0].text() == "USD") {
                                database?.saveCurs(
                                    bank = "prva",
                                    bankLabel = "Prva",
                                    euro = tdItem[3].text().toDouble(),
                                    usd = tdItem[1].text().toDouble()
                                )
                            }
                        }
                    }
                }
            }
        }catch (e: Exception){
            printCustom("Prva: ${e.message}")
        }
    }

    private fun ziraat(){
        restApiServiceZiraat?.getCurs { response ->
            if (response != null){
                try {
                    val dollar = response.first() {
                        it.name == "USD"
                    }
                    database?.saveCurs(
                        bank = "ziraat",
                        bankLabel = "Ziraat",
                        euro = 0.0,
                        usd = 1 / dollar.value
                    )

                }catch (e: Exception){
                    printCustom("Ziraat: $e")
                }
            }else{
                printCustom("Ziraat: null")
            }
        }
    }

    fun invoke(){
        ckb()
        erste()
        hipotekarna()
        lovcen()
        nlb()
        prva()
        ziraat()
    }

}