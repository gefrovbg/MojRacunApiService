package database.data.racun

import database.data.UserEntity.Companion.referrersOn
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Sellers: IntIdTable("sellers") {
    val address = varchar("address", 100)
    val country = varchar("country", 100)
    val idNum = varchar("idNum", 100)
    val idType = varchar("idType", 100)
    val name = varchar("name", 100)
    val town = varchar("town", 100)
}

class SellerEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<SellerEntity>(Sellers)

    var address by Sellers.address
    var country by Sellers.country
    var idNum by Sellers.idNum
    var idType by Sellers.idType
    var name by Sellers.name
    var town by Sellers.town

    val racun by RacunEntity referrersOn Racuns.user

    fun toSeller() = Seller(
        address = address,
        country = country,
        idNum = idNum,
        idType = idType,
        name = name,
        town = town
    )
}

data class Seller(
    val address: String? = null,
    val country: String? = null,
    val idNum: String? = null,
    val idType: String? = null,
    val name: String? = null,
    val town: String? = null
)