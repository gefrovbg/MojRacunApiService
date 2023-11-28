package database.data

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Banks: IntIdTable() {
    val name = varchar("name", 50)
    val labelName = varchar("labelName", 50)
    val imageUrl = varchar("imageUrl", 100)
}

class BankEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<BankEntity>(Banks)

    var name by Banks.name
    var imageUrl by Banks.imageUrl
    val curses by CursEntity referrersOn Curses.bank
    var labelName by Banks.labelName

    fun toBank(): Bank {
//        val _curses = mutableListOf<Curs>()
//
//        curses.forEach {
//            _curses.add(it.toCurs())
//        }

        return Bank(
            id = id.value,
            name = name,
            imageUrl = imageUrl,
//            curses = _curses,
            labelName = labelName
        )
    }
}

data class Bank(
    val id: Int,
    val name: String,
    val labelName: String,
    val imageUrl: String,
//    val curses: List<Curs>
)

sealed class BankWithGroupedCurses {
    data class BankWithListCurses<T>(
        val id: Int,
        val name: String,
        val labelName: String,
        val imageUrl: String,
        val curses: List<List<T>>
    ){
        constructor(bank: Bank, curses: List<List<T>>):this(
            id = bank.id,
            name = bank.name,
            labelName = bank.labelName,
            imageUrl = bank.imageUrl,
            curses = curses
        )
    }

    data class BankWithGroupedListCurses<T>(
        val id: Int,
        val name: String,
        val labelName: String,
        val imageUrl: String,
        val curses: List<T>
    ){
        constructor(bank: Bank, curses: List<T>):this(
            id = bank.id,
            name = bank.name,
            labelName = bank.labelName,
            imageUrl = bank.imageUrl,
            curses = curses
        )
    }
}

data class BankWithListCurses(
    val id: Int,
    val name: String,
    val labelName: String,
    val imageUrl: String,
    val curses: List<CursForList>
){
    constructor(bank: Bank, curses: List<CursForList>):this(
        id = bank.id,
        name = bank.name,
        labelName = bank.labelName,
        imageUrl = bank.imageUrl,
        curses = curses
    )
}
