package banks.api.retrofit.racun.data

data class SameTaxe(
    val exemptFromVat: Any?,
    val id: Int?,
    val numberOfItems: Int?,
    val priceBeforeVat: Double?,
    val vatAmount: Double?,
    val vatRate: Int?
)