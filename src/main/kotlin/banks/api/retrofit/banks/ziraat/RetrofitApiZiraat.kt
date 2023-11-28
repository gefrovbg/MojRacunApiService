package banks.api.retrofit.banks.ziraat

import banks.api.retrofit.banks.ziraat.data.ZiraatResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface RetrofitApiZiraat {

    @Headers("Content-Type: application/json")
    @GET("tr/GetCurrency")
    fun getInfo(): Call<ZiraatResponse>

}