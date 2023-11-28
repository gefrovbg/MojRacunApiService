package banks.api.retrofit.racun

import database.data.racun.Racun
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApiRacun {

    @POST("/ic/api/verifyInvoice")
    @Multipart
    fun getInfo(
        @Part("iic") iic: RequestBody,
        @Part("dateTimeCreated") dateTimeCreated: RequestBody,
        @Part("tin") tin: RequestBody
    ): Call<Racun>

}