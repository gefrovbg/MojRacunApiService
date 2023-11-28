package banks.api.retrofit.banks.erste

import banks.api.retrofit.banks.erste.data.ErsteResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface RetrofitApiErste {
    @Headers("Content-Type: application/json")
    @GET("rproxy/webdocapi/ebmn/fx/current")
    fun getInfo(): Call<ErsteResponse>
}