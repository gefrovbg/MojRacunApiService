package endpoints.racun

import banks.api.retrofit.racun.RestApiServiceRacun
import banks.api.retrofit.racun.data.RequestBodyRacunInfo
import database.data.racun.Racun
import database.data.racun.RacunState
import database.data.racun.response.RacunInfoResponse
import database.racun.RacunDatabase
import endpoints.dto.ApiException
import endpoints.dto.LocalDateRequest
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import security.service.TokenService
import services.racun.RacunServices
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/racun")
class RacunController {

    @Autowired
    private val restApiServiceRacun: RestApiServiceRacun? = null

    @Autowired
    private val racunDatabase: RacunDatabase? = null

    @Autowired
    private val racunServices: RacunServices? = null

    @Autowired
    private val tokenService: TokenService? = null

    @GetMapping("/get")
    suspend fun getInfo(
        @RequestParam("date") dateBefore: String,
        @RequestParam ("scale") scale: String,
        @RequestHeader("Authorization") bearerToken: String
    ): ResponseEntity<RacunInfoResponse?> {
        val token = if (bearerToken.contains("Bearer ")) bearerToken.removePrefix("Bearer ") else bearerToken

        val date = LocalDate.parse(dateBefore)

        return try {
            if (token.isNotBlank()) {
                val user = tokenService?.parseToken(token)
                if (user != null) {
                    when (scale) {
                        "day" -> ResponseEntity.status(HttpStatus.OK)
                            .body(racunDatabase?.getInfoByDate(userMail = user.mail, date = date))

                        "month" -> ResponseEntity.status(HttpStatus.OK)
                            .body(racunDatabase?.getInfoByMonth(userMail = user.mail, date = date))

                        "custom" -> ResponseEntity.status(HttpStatus.OK)
                            .body(racunDatabase?.getInfoByDate(userMail = user.mail, date = date))

                        else -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
                    }

                } else {
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
                }
            } else {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
            }
        } catch (e: Exception){
            ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null)
        }
    }

    @GetMapping("/get-info-by-date")
    suspend fun getInfoByDate(
        @RequestBody date: LocalDateRequest,
        @RequestHeader("Authorization") bearerToken: String
    ): ResponseEntity<RacunInfoResponse?> {
        val token = if (bearerToken.contains("Bearer ")) bearerToken.removePrefix("Bearer ") else bearerToken

        return if (token.isNotBlank()) {
            val user = tokenService?.parseToken(token)
            if (user != null)
                ResponseEntity.status(HttpStatus.OK).body(racunDatabase?.getInfoByDate(userMail = user.mail, date = date.date))
            else{
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }
    }


    @GetMapping("/get-info-by-month")
    suspend fun getInfoByMonth(
        @RequestBody date: LocalDateRequest,
        @RequestHeader("Authorization") bearerToken: String
    ): ResponseEntity<RacunInfoResponse?> {
        val token = if (bearerToken.contains("Bearer ")) bearerToken.removePrefix("Bearer ") else bearerToken

        return if (token.isNotBlank()) {
            val user = tokenService?.parseToken(token)
            if (user != null)
                ResponseEntity.status(HttpStatus.OK).body(racunDatabase?.getInfoByMonth(userMail = user.mail, date = date.date))
            else{
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }
    }

    @PostMapping("/add")
    suspend fun addRacun(
        @RequestBody requestBody: RequestBodyRacunInfo,
        @RequestHeader("Authorization") bearerToken: String
    ) {
        val token = if (bearerToken.contains("Bearer ")) bearerToken.removePrefix("Bearer ") else bearerToken

        if (token.isNotBlank()) {
            val user = tokenService?.parseToken(token)
            if (racunServices != null) {
                val apiException = racunServices.addRacun(user = user, requestBody = requestBody)
                if (apiException != null)
                    throw apiException
            }else
                throw ApiException(500, "Service not already!")
        } else {
            throw ApiException(401, "Token not found!")
        }
    }

}