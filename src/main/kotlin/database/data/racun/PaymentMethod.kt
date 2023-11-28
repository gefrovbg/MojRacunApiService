package database.data.racun

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object PaymentMethods: IntIdTable("payment_methods"){
    val identifierNumber = long("identifierNumber")
    val amount = double("amount")
    val typeCode = varchar("typeCode", 250)
    val type = varchar("type", 250)

    val racun = reference("racun", Racuns)
}

class PaymentMethodEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<PaymentMethodEntity>(PaymentMethods)

    var identifierNumber by PaymentMethods.identifierNumber
    var amount by PaymentMethods.amount
    var typeCode by PaymentMethods.typeCode
    var type by PaymentMethods.type

    var racun by RacunEntity referencedOn PaymentMethods.racun

    fun toPaymentMethod() = PaymentMethod(
        id = identifierNumber,
        amount = amount,
        type = type,
        typeCode = typeCode
    )
}

data class PaymentMethod(
    val advIIC: Any? = null,
    val amount: Double? = null,
    val bankAcc: Any? = null,
    val compCard: Any? = null,
    val id: Long? = null,
    val type: String? = null,
    val typeCode: String? = null,
    val vouchers: Any? = null
)