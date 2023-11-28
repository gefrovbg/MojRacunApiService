package database.data.racun

import banks.api.retrofit.racun.data.SameTaxe
import database.data.UserEntity
import database.data.Users
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import tools.toLocalDateTime
import tools.toZoneDateTime

object Racuns: LongIdTable("racuns"){
    val iic = varchar("iic", 100)
    val dateTimeCreated = datetime("dateTimeCreated")
//    val dateTimeCreated = varchar("dateTimeCreated", 100)
    val totalPrice = double("totalPrice")
    val totalPriceWithoutVAT = double("totalPriceWithoutVAT")
    val totalVATAmount = double("totalVATAmount")
    val identifierNumber = long("identifierNumber")
    val issuerTaxNumber = long("issuerTaxNumber")

    val seller = reference("seller", Sellers)
    val user = reference("user", Users)

    val status = varchar("status", 100)
//    val status = enumeration("status", RacunState::class)
}

class RacunEntity(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<RacunEntity>(Racuns)

    var iic by Racuns.iic
    var dateTimeCreated by Racuns.dateTimeCreated
    var totalPrice by Racuns.totalPrice
    var totalPriceWithoutVAT by Racuns.totalPriceWithoutVAT
    var totalVATAmount by Racuns.totalVATAmount
    var identifierNumber by Racuns.identifierNumber
    var issuerTaxNumber by Racuns.issuerTaxNumber
    var seller by SellerEntity referencedOn Racuns.seller
    var user by UserEntity referencedOn Racuns.user
    val items by RacunItemEntity referrersOn RacunItems.racun
    val paymentMethod by PaymentMethodEntity referrersOn PaymentMethods.racun
    var status by Racuns.status

    val itemsList = {
        val list = mutableListOf<RacunItem>()
        items.forEach { racunItemEntity ->
            list.add(racunItemEntity.toRacunItem())
        }
        list
    }

    val paymentMethodList = {
        val list = mutableListOf<PaymentMethod>()
        paymentMethod.forEach { paymentMethodEntity ->
            list.add(paymentMethodEntity.toPaymentMethod())
        }
        list
    }


    fun toRacun() = Racun(
        id = id.value,
        iic = iic,
//        dateTimeCreated = dateTimeCreated.toZoneDateTime().toString().substringBeforeLast('['),
        dateTimeCreated = dateTimeCreated.toString(),
        totalPrice = totalPrice,
        totalPriceWithoutVAT = totalPriceWithoutVAT,
        totalVATAmount = totalVATAmount,
        identifierNumber = identifierNumber,
        issuerTaxNumber = issuerTaxNumber.toString(),
        seller = seller.toSeller(),
        status = status,
        items = itemsList(),
        paymentMethod = paymentMethodList()
    )

}

data class Racun(
    val active: Any? = null,
    val approvals: List<Any>? = null,
    val badDebt: Boolean? = null,
    val badDebtInvoice: Any? = null,
    val baddeptInv: Any? = null,
    val bankAccNum: Any? = null,
    val businessUnit: String? = null,
    val buyer: Any? = null,
    val cashRegister: String? = null,
    val correctiveInvoiceType: Any? = null,
    val createdBy: Any? = null,
    val creationDate: Any? = null,
    val currency: Any? = null,
    val dateTimeCreated: String? = null,
    val fees: Any? = null,
    val fic: String? = null,
    val goodsExAmt: Any? = null,
    val id: Long? = null,
    val identifierNumber: Long? = null,
    val iic: String? = null,
    val iicRefIssuingDate: Any? = null,
    val iicReference: Any? = null,
    val iicRefs: Any? = null,
    val iicSignature: String? = null,
    val invoiceNumber: String? = null,
    val invoiceOrderNumber: Int? = null,
    val invoiceRequest: Any? = null,
    val invoiceType: String? = null,
    val invoiceVersion: Int? = null,
    val isReverseCharge: Boolean? = null,
    val isSimplifiedInvoice: Boolean? = null,
    val issuerInVat: Boolean? = null,
    val issuerTaxNumber: String? = null,
    val items: List<RacunItem>? = null,
    val lastUpdateDate: Any? = null,
    val lastUpdatedBy: Any? = null,
    val listOfCorrectedInvoiceIIC: List<Any>? = null,
    val markUpAmt: Any? = null,
    val note: Any? = null,
    val operatorCode: String? = null,
    val originalInvoice: Any? = null,
    val paragonBlockNum: Any? = null,
    val payDeadline: Any? = null,
    val paymentMethod: List<PaymentMethod>? = null,
    val sameTaxes: List<SameTaxe>? = null,
    val seller: Seller? = null,
    val softwareCode: String? = null,
    val supplyDateOrPeriod: Any? = null,
    val taxFreeAmt: Any? = null,
    val taxPeriod: Any? = null,
    val tcrCode: String? = null,
    val totalPrice: Double? = null,
    val totalPriceToPay: Double? = null,
    val totalPriceWithoutVAT: Double? = null,
    val totalVATAmount: Double? = null,
    val typeOfInvoice: String? = null,
    val typeOfSelfIss: Any? = null,
    val status: String? = null,
)

object RacunState{
    const val ShortData = "ShortData"
    const val FullData = "FullData"
    const val Error = "Error"
    const val FinalError = "FinalError"
    const val Manual = "Manual"
}

//enum class RacunState{
//    ShortData,
//    FullData,
//    Error,
//    FinalError,
//    Manual
//}