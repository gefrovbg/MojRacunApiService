package database.racun

import database.data.UserEntity
import database.data.Users
import database.data.racun.*
import database.data.racun.response.RacunInfoResponse
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.or
import org.springframework.stereotype.Service
import tools.generateRandomId
import tools.toLocalDateTime
import tools.toZoneDateTime
import tools.transactionCustom
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.math.roundToInt

@Service
class RacunDatabaseImpl: RacunDatabase {

    override fun saveRacun(racun: Racun, userMail: String, status: String) {
        transactionCustom {
            val id = racun.iic ?: ""
            val findRacun = RacunEntity.find {
                Racuns.iic eq id
            }.firstOrNull()
            if (findRacun == null) {
                val _racun = RacunEntity.new {
                    println()
                    this.iic = racun.iic ?: ""
//                    this.dateTimeCreated = racun.dateTimeCreated?.toZoneDateTime()?.toLocalDateTime() ?: LocalDateTime.now()
                    this.dateTimeCreated = racun.dateTimeCreated?.toLocalDateTime() ?: LocalDateTime.now()
                    this.identifierNumber = racun.id ?: generateRandomId()
                    this.issuerTaxNumber = racun.issuerTaxNumber?.toLong() ?: 0L
                    this.totalPrice = racun.totalPrice ?: 0.0
                    this.totalVATAmount = racun.totalVATAmount ?: 0.0
                    this.totalPriceWithoutVAT = racun.totalPriceWithoutVAT ?: 0.0
                    this.seller = SellerEntity.find {
                        val idNum = racun.seller?.idNum ?: ""
                        Sellers.idNum eq idNum
                    }.firstOrNull() ?: SellerEntity.new {
                        this.idNum = racun.seller?.idNum ?: ""
                        this.name = racun.seller?.name ?: ""
                        this.country = racun.seller?.country ?: ""
                        this.idType = racun.seller?.idType ?: ""
                        this.town = racun.seller?.town ?: ""
                        this.address = racun.seller?.address ?: ""
                    }
                    this.user = UserEntity.find {
                        Users.mail eq userMail
                    }.first()
                    this.status = status
                }
                println(_racun.toRacun())
                if (!racun.items.isNullOrEmpty()) {
                    racun.items.forEach { racunItem ->
                        val item = RacunItemEntity.new {
                            this.code = racunItem.code ?: ""
                            this.identifierNumber = racunItem.id ?: generateRandomId()
                            this.name = racunItem.name ?: ""
                            this.priceAfterVat = racunItem.priceAfterVat ?: 0.0
                            this.priceBeforeVat = racunItem.priceBeforeVat ?: 0.0
                            this.quantity = racunItem.quantity ?: 0.0
                            this.rebate = racunItem.rebate ?: 0.0
                            this.rebateReducing = racunItem.rebateReducing ?: false
                            this.unit = racunItem.unit ?: ""
                            this.unitPriceAfterVat = racunItem.unitPriceAfterVat ?: 0.0
                            this.unitPriceBeforeVat = racunItem.unitPriceBeforeVat ?: 0.0
                            this.vatAmount = racunItem.vatAmount ?: 0.0
                            this.vatRate = racunItem.vatRate ?: 0
                            this.racun = _racun
                            this.priceWithoutRebate = if (racunItem.rebate != null) {
                                if (racunItem.rebate > 0) {
                                    val bigDecimalX = BigDecimal.valueOf(racunItem.priceAfterVat ?: 0.0)
                                    val bigDecimalY = BigDecimal.valueOf(racunItem.rebate ?: 0.0)
                                    val result = bigDecimalX.divide(BigDecimal(100).subtract(bigDecimalY), 100, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP)
                                    result.toDouble()
                                } else racunItem.priceAfterVat ?: 0.0
                            } else racunItem.priceAfterVat ?: 0.0
                        }
                        println(item.toRacunItem())
                    }
                }
                if (!racun.paymentMethod.isNullOrEmpty()) {
                    racun.paymentMethod.forEach { paymentMethod ->
                        val item = PaymentMethodEntity.new {
                            this.identifierNumber = paymentMethod.id?.toLong() ?: generateRandomId()
                            this.type = paymentMethod.type ?: ""
                            this.amount = paymentMethod.amount ?: 0.0
                            this.typeCode = paymentMethod.typeCode ?: ""
                            this.racun = _racun
                        }
                        println(item.toPaymentMethod())
                    }
                }
            }else{
                findRacun.let {
                    it.iic = racun.iic ?: ""
//                    it.dateTimeCreated = racun.dateTimeCreated ?: ""
//                    it.dateTimeCreated = racun.dateTimeCreated?.toZoneDateTime()?.toLocalDateTime() ?: LocalDateTime.now()
                    it.identifierNumber = racun.id ?: generateRandomId()
                    it.issuerTaxNumber = racun.issuerTaxNumber?.toLong() ?: 0L
                    it.totalPrice = racun.totalPrice ?: 0.0
                    it.totalVATAmount = racun.totalVATAmount ?: 0.0
                    it.totalPriceWithoutVAT = racun.totalPriceWithoutVAT ?: 0.0
                    it.seller = SellerEntity.find {
                        val idNum = racun.seller?.idNum ?: ""
                        Sellers.idNum eq idNum
                    }.firstOrNull() ?: SellerEntity.new {
                        this.idNum = racun.seller?.idNum ?: ""
                        this.name = racun.seller?.name ?: ""
                        this.country = racun.seller?.country ?: ""
                        this.idType = racun.seller?.idType ?: ""
                        this.town = racun.seller?.town ?: ""
                        this.address = racun.seller?.address ?: ""
                    }
                    it.user = UserEntity.find {
                        Users.mail eq userMail
                    }.first()
                    it.status = status
                }
                if (!racun.items.isNullOrEmpty()) {
                    racun.items.forEach { racunItem ->
                        val item = RacunItemEntity.new {
                            this.code = racunItem.code ?: ""
                            this.identifierNumber = racunItem.id ?: generateRandomId()
                            this.name = racunItem.name ?: ""
                            this.priceAfterVat = racunItem.priceAfterVat ?: 0.0
                            this.priceBeforeVat = racunItem.priceBeforeVat ?: 0.0
                            this.quantity = racunItem.quantity ?: 0.0
                            this.rebate = racunItem.rebate ?: 0.0
                            this.rebateReducing = racunItem.rebateReducing ?: false
                            this.unit = racunItem.unit ?: ""
                            this.unitPriceAfterVat = racunItem.unitPriceAfterVat ?: 0.0
                            this.unitPriceBeforeVat = racunItem.unitPriceBeforeVat ?: 0.0
                            this.vatAmount = racunItem.vatAmount ?: 0.0
                            this.vatRate = racunItem.vatRate ?: 0
                            this.racun = findRacun
                            this.priceWithoutRebate =  if (racunItem.rebate != null) {
                                if (racunItem.rebate > 0) {
                                    val bigDecimalX = BigDecimal.valueOf(racunItem.priceAfterVat ?: 0.0)
                                    val bigDecimalY = BigDecimal.valueOf(racunItem.rebate ?: 0.0)
                                    val result = bigDecimalX.divide(BigDecimal(100).subtract(bigDecimalY), 100, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP)
                                    result.toDouble()
                                } else racunItem.priceAfterVat ?: 0.0
                            } else racunItem.priceAfterVat ?: 0.0
                        }
                        println(item.toRacunItem())
                    }
                }
                if (!racun.paymentMethod.isNullOrEmpty()) {
                    racun.paymentMethod.forEach { paymentMethod ->
                        val item = PaymentMethodEntity.new {
                            this.identifierNumber = paymentMethod.id?.toLong() ?: generateRandomId()
                            this.type = paymentMethod.type ?: ""
                            this.amount = paymentMethod.amount ?: 0.0
                            this.typeCode = paymentMethod.typeCode ?: ""
                            this.racun = findRacun
                        }
                        println(item.toPaymentMethod())
                    }
                }
            }
        }
    }

    override fun checkRacun(racun: Racun, userMail: String): String? {
        return transactionCustom{
            val user = UserEntity.find {
                Users.mail eq userMail
            }.firstOrNull()
            return@transactionCustom if (user != null) {
                val _racun = user.racun.find {
                    it.iic == racun.iic
                }
                _racun?.status

            } else
                null
        }
    }

    override fun getInfoByDate(userMail: String, date: LocalDate): RacunInfoResponse? {
        val startOfDay = date.atStartOfDay()
        val endOfDay = date.atTime(LocalTime.MAX)
        return transactionCustom {
            val user = UserEntity.find {
                Users.mail eq userMail
            }.firstOrNull()
            return@transactionCustom if (user != null) {
                val racunList = RacunEntity.find {
                    (Racuns.user eq user.id) and Racuns.dateTimeCreated.between(startOfDay,endOfDay)
                }.map {
                    it.toRacun()
                }
                RacunInfoResponse(
                    date = date,
                    amount = (racunList.sumOf {
                        it.totalPrice ?: 0.0
                    }*100).roundToInt()/100.0,
                    racunList = racunList
                )
            } else
                null
        }
    }

    override fun getInfoByMonth(userMail: String, date: LocalDate): RacunInfoResponse? {
        val startOfMonth = date.withDayOfMonth(1).atStartOfDay()
        val endOfMonth = date.withDayOfMonth(date.lengthOfMonth()).atTime(LocalTime.MAX)

        return transactionCustom {
            val user = UserEntity.find {
                Users.mail eq userMail
            }.firstOrNull()

            return@transactionCustom if (user != null) {
                val racunList = RacunEntity.find {
                    (Racuns.user eq user.id) and Racuns.dateTimeCreated.between(startOfMonth, endOfMonth)
                }.map {
                    it.toRacun()
                }

                RacunInfoResponse(
                    date = date,
                    amount = (racunList.sumOf {
                        it.totalPrice ?: 0.0
                    } * 100).roundToInt() / 100.0,
                    racunList = racunList
                )
            } else {
                null
            }
        }
    }

    override fun getRacunsListForCheck(): Map<String,List<Racun>> {
        return transactionCustom {

            val returnMap = try {
                UserEntity.all().associate { user ->
                    val key = user.mail
                    val value = RacunEntity.find {
                        (Racuns.status eq RacunState.Error) and (Racuns.user eq user.id)
                    }.map { it.toRacun() }
                    key to value
                }
            }catch (e: Exception){
                emptyMap<String,MutableList<Racun>>()
            }
            return@transactionCustom returnMap
        }
    }

    override fun updateRacunStatus(status: String, id: Long?) {
        transactionCustom {
            RacunEntity.find {
                Racuns.id eq id
            }.firstOrNull()?.status = status
        }
    }
}