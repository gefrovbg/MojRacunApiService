package banks.api.retrofit.racun.data

data class Item(
    val code: String?,
    val exemptFromVat: Any?,
    val id: Long?,
    val investment: Boolean?,
    val name: String?,
    val priceAfterVat: Double?,
    val priceBeforeVat: Double?,
    val quantity: Double?,
    val rebate: Any?,
    val rebateReducing: Boolean?,
    val unit: String?,
    val unitPriceAfterVat: Double?,
    val unitPriceBeforeVat: Double?,
    val vatAmount: Double?,
    val vatRate: Int?,
    val vd: Any?,
    val voucherSold: Any?,
    val vsn: Any?
)