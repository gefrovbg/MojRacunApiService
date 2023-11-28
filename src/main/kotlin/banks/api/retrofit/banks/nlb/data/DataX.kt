package banks.api.retrofit.banks.nlb.data

data class DataX(
    val code: String,
    val effective_buy: Double,
    val effective_sell: Double,
    val exchange_rate_id: Int,
    val foreign_buy: Double,
    val foreign_sell: Double,
    val id: Int,
    val middle: Double,
    val name: String,
    val unique_id: Int
)