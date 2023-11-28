package tools

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toZoneDateTime(): ZonedDateTime{

    return this.atZone(ZoneId.of("Europe/Podgorica"))

}

fun String.toZoneDateTime(): ZonedDateTime{

    return try {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        ZonedDateTime.parse(this, formatter)
    }catch (e: Exception){
        printCustom("toZoneDateTime: ${e.message}")
        try {
            ZonedDateTime.parse(this)
        }catch (e: Exception){
            printCustom("toZoneDateTime: ${e.message}")
            ZonedDateTime.now()
        }
    }

}

//fun ZonedDateTime.toLocalDateTimeCustom(): LocalDateTime{
//    return try {
//
//    }catch (e:Exception){
//        printCustom("ZonedDateTime.toLocalDateTimeCustom: ${e.message}")
//        try {
//
//        }catch (e:Exception){
//            printCustom("ZonedDateTime.toLocalDateTimeCustom: ${e.message}")
//
//        }
//    }
//}

fun String.toLocalDateTime(): LocalDateTime{

    return try {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        LocalDateTime.parse(this.substringBefore('+'), formatter)
    }catch (e: Exception){
        printCustom("toLocalDateTime: ${e.message}")
        try {
            LocalDateTime.parse(this.substringBefore('+'))
        }catch (e: Exception){
            printCustom("toLocalDateTime: ${e.message}")
            LocalDateTime.now()
        }
    }

}