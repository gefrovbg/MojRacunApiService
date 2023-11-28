package banks.api.retrofit.racun.data

data class PaymentMethod(
    val advIIC: Any?,
    val amount: Double?,
    val bankAcc: Any?,
    val compCard: Any?,
    val id: Int?,
    val type: String?,
    val typeCode: String?,
    val vouchers: Any?
)