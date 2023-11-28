package banks.api.retrofit.banks.nlb.data

data class NLBResponse(
    val data: Data,
    val message: String,
    val status_code: Int
)