package services.racun

import banks.api.retrofit.racun.RestApiServiceRacun
import banks.api.retrofit.racun.data.RequestBodyRacunInfo
import database.data.User
import database.data.racun.Racun
import database.data.racun.RacunState
import database.racun.RacunDatabase
import endpoints.dto.ApiException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tools.toLocalDateTime
import tools.toZoneDateTime

@Service
class RacunServices {

    @Autowired
    private val database: RacunDatabase? = null

    @Autowired
    private val restApiServiceRacun: RestApiServiceRacun? = null

    fun addRacun(
        user: User?,
        requestBody: RequestBodyRacunInfo
    ): ApiException?{
        val racun = Racun(
            iic = requestBody.iic,
            dateTimeCreated = requestBody.dateTimeCreated,
            issuerTaxNumber = requestBody.tin,
            totalPrice = requestBody.amount
        )
        return if (user != null) {
            when(database?.checkRacun(racun = racun, userMail = user.mail)){
                RacunState.FullData,
                RacunState.ShortData,
                RacunState.Error,
                RacunState.FinalError,
                RacunState.Manual -> {
                    ApiException(409, "Racun already exists")
                }
                else -> {
                    println("requestBody.manual ${requestBody.manual}")
                    database?.saveRacun(
                        racun = racun,
                        userMail = user.mail,
                        status = if (requestBody.manual) RacunState.Manual else RacunState.ShortData
                    )
                    if (!requestBody.manual)
                        restApiServiceRacun?.getInfo(
                            iic = requestBody.iic,
                            dateTimeCreated = requestBody.dateTimeCreated,
                            tin = requestBody.tin,
                            onResult = { result ->
                                println(result)
                                if (result != null)
                                    database?.saveRacun(result, user.mail, RacunState.FullData)
                                else{
                                    database?.updateRacunStatus(status = if (racun.status == RacunState.Error) RacunState.FinalError else RacunState.Error, id = racun.id)
                                }
                            }
                        )
                    null
                }
            }
        } else
            ApiException(401, "User not found!")
    }

    fun checkRacuns(){
        val racuns = database?.getRacunsListForCheck()

        if (!racuns.isNullOrEmpty()){
            racuns.forEach { map ->
                if (map.value.isNotEmpty()){
                    for (racun in map.value) {
                        if (!racun.iic.isNullOrBlank() && !racun.issuerTaxNumber.isNullOrBlank() && !racun.dateTimeCreated.isNullOrBlank()) {
                            val dateTime = racun.dateTimeCreated.toLocalDateTime().toZoneDateTime().toString()
                            restApiServiceRacun?.getInfo(
                                iic = racun.iic,
                                dateTimeCreated = if (dateTime.contains('[')) dateTime.substringBefore('[') else dateTime,
                                tin = racun.issuerTaxNumber,
                                onResult = { result ->
                                    println("checkRacuns: result: $result")
                                    if (result != null)
                                        database?.saveRacun(result, map.key, RacunState.FullData)
                                    else {
                                        database?.updateRacunStatus(
                                            status = if (racun.status == RacunState.Error) RacunState.FinalError else RacunState.Error,
                                            id = racun.id
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}