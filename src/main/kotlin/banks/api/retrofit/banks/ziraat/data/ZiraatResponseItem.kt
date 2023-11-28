package banks.api.retrofit.banks.ziraat.data

data class ZiraatResponseItem(
    val active: Boolean,
    val currencyID: Int,
    val difference: Double,
    val effectiveBuy: Double,
    val effectiveSell: Double,
    val id: Int,
    val name: String,
    val oldValue: Double,
    val orderNumber: Int,
    val tenantID: Int,
    val unit: Int,
    val value: Double
)