package banks.api.retrofit.banks.nlb

import banks.api.retrofit.banks.nlb.data.NLBResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApiNLB {
    @Headers("Content-Type: application/json")
    @GET("{date}")
    fun getInfo(@Path("date") date:String ,@Query("extended_fields") apikey: String? = "currencies"): Call<NLBResponse>
}