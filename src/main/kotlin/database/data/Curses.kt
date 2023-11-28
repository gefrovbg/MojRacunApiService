package database.data

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Curses: IntIdTable() {
    val dateTime = datetime("dateTime")
    val euro = double("euro")
    val usd = double("usd")
    val bank = reference("bank", Banks)
}

class CursEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<CursEntity>(Curses)

    var dateTime by Curses.dateTime
    var euro by Curses.euro
    var usd by Curses.usd
    var bank by BankEntity referencedOn Curses.bank

    fun toCurs() = Curs(id = id.value, dateTime = dateTime, euro = euro, usd = usd, bank = bank.toBank())

    fun toCursForList() = CursForList(toCurs())

    fun toCursDollar() = CursDollar(toCurs())

    fun toCursEuro() = CursEuro(toCurs())
}

data class Curs(
    val id: Int,
    val dateTime: LocalDateTime,
    val euro: Double,
    val usd: Double,
    val bank: Bank
)

data class CursForList(
    val id: Int,
    val dateTime: LocalDateTime,
    val euro: Double,
    val usd: Double
){
    constructor(curs: Curs): this(
        id = curs.id,
        dateTime = curs.dateTime,
        euro = curs.euro,
        usd = curs.usd
    )
}

data class CursDollar(
    val id: Int,
    val dateTimeStart: LocalDateTime,
    val dateTimeStop: LocalDateTime,
    val value: Double,
){
    constructor(curs: Curs): this(
        id = curs.id,
        dateTimeStart = curs.dateTime,
        dateTimeStop = curs.dateTime,
        value = curs.usd
    )
}

data class CursEuro(
    val id: Int,
    val dateTimeStart: LocalDateTime,
    val dateTimeStop: LocalDateTime,
    val value: Double,
){
    constructor(curs: Curs): this(
        id = curs.id,
        dateTimeStart = curs.dateTime,
        dateTimeStop = curs.dateTime,
        value = curs.euro
    )
}