package banks.api.retrofit.banks.ckb

import banks.api.retrofit.banks.ckb.data.CKBResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApiCKB {
    @Headers("Content-Type: application/json")
    @GET("ajax_getcurrency_value.aspx")
    fun getInfo(@Query("id") id: Int, @Query("datumOd") datumOd: String, @Query("datumDo") datumDo: String): Call<CKBResponse>
}