package banks.api.retrofit.racun.data

data class RequestBodyRacunInfo(
    val iic: String,
    val dateTimeCreated: String,
    val tin: String,
    val amount: Double,
    val manual: Boolean = true
)
