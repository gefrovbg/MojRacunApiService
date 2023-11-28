package banks.api.retrofit.banks.erste.data

data class ErsteResponse(
    val currencies: List<Currency>,
    val date: String,
    val dateFormatted: String,
    val number: Int
)