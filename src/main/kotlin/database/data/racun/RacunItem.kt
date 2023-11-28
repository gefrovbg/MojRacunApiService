package database.data.racun

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object RacunItems: IntIdTable("racun_items"){
    val code = varchar("code", 250)
    val identifierNumber = long("identifierNumber")
    val name = varchar("name", 250)
    val priceAfterVat = double("priceAfterVat")
    val priceBeforeVat = double("priceBeforeVat")
    val quantity = double("quantity")
    val rebate = double("rebate")
    val rebateReducing = bool("rebateReducing")
    val unit = varchar("unit", 250)
    val unitPriceAfterVat = double("unitPriceAfterVat")
    val unitPriceBeforeVat = double("unitPriceBeforeVat")
    val vatAmount = double("vatAmount")
    val vatRate = integer("vatRate")
    val priceWithoutRebate = double("priceWithoutRebate").nullable()

    val racun = reference("racun", Racuns)
}

class RacunItemEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<RacunItemEntity>(RacunItems)

    var code by RacunItems.code
    var identifierNumber by RacunItems.identifierNumber
    var name by RacunItems.name
    var priceAfterVat by RacunItems.priceAfterVat
    var priceBeforeVat by RacunItems.priceBeforeVat
    var quantity by RacunItems.quantity
    var rebate by RacunItems.rebate
    var rebateReducing by RacunItems.rebateReducing
    var unit by RacunItems.unit
    var unitPriceAfterVat by RacunItems.unitPriceAfterVat
    var unitPriceBeforeVat by RacunItems.unitPriceBeforeVat
    var vatAmount by RacunItems.vatAmount
    var vatRate by RacunItems.vatRate
    var priceWithoutRebate by RacunItems.priceWithoutRebate

    var racun by RacunEntity referencedOn RacunItems.racun

    fun toRacunItem() = RacunItem(
        code = code,
        id = identifierNumber,
        name = name,
        priceAfterVat = priceAfterVat,
        priceBeforeVat = priceBeforeVat,
        quantity = quantity,
        rebate = rebate,
        rebateReducing = rebateReducing,
        unit = unit,
        unitPriceAfterVat = unitPriceAfterVat,
        unitPriceBeforeVat = unitPriceBeforeVat,
        vatAmount = vatAmount,
        vatRate = vatRate,
        priceWithoutRebate = priceWithoutRebate
    )

}

data class RacunItem(
    val code: String? = null,
    val exemptFromVat: Any? = null,
    val id: Long? = null,
    val investment: Boolean? = null,
    val name: String? = null,
    val priceAfterVat: Double? = null,
    val priceBeforeVat: Double? = null,
    val quantity: Double? = null,
    val rebate: Double? = null,
    val rebateReducing: Boolean? = null,
    val unit: String? = null,
    val unitPriceAfterVat: Double? = null,
    val unitPriceBeforeVat: Double? = null,
    val vatAmount: Double? = null,
    val vatRate: Int? = null,
    val vd: Any? = null,
    val voucherSold: Any? = null,
    val vsn: Any? = null,
    val priceWithoutRebate: Double? = null
)