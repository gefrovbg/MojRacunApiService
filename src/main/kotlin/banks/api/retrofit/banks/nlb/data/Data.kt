package banks.api.retrofit.banks.nlb.data

data class Data(
    val created_at: String,
    val creator_id: Int,
    val currencies: Currencies,
    val date: String,
    val id: Int,
    val updated_at: String
)