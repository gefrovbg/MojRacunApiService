package banks.api.retrofit.banks.erste.data

data class Currency(
    val amount: Int,
    val buy: Double,
    val buyNotes: Double,
    val code: String,
    val hnb: Double,
    val mid: Double,
    val name: String,
    val sell: Double,
    val sellNotes: Double
)