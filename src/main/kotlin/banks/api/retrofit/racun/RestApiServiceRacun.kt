package banks.api.retrofit.racun

import database.data.racun.Racun
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tools.printCustom

@Service
class RestApiServiceRacun {

    fun getInfo(
        iic: String,
        dateTimeCreated: String,
        tin: String,
        onResult:(Racun?) -> Unit
    ) {
        println("iic: $iic    dtcrt: $dateTimeCreated    tin: $tin")

        val iicRequestBody = iic.toRequestBody("text/plain".toMediaType())
        val dateTimeCreatedRequestBody = dateTimeCreated.toRequestBody("text/plain".toMediaType())
        val tinRequestBody = tin.toRequestBody("text/plain".toMediaType())

        println("iic: $iicRequestBody    dtcrt: $dateTimeCreatedRequestBody    tin: $tinRequestBody")

        val retrofit = ServiceBuilderRacun.buildService(RetrofitApiRacun::class.java)
        retrofit.getInfo(
            iic = iicRequestBody,
            dateTimeCreated = dateTimeCreatedRequestBody,
            tin = tinRequestBody
        ).enqueue(
            object : Callback<Racun>{
                override fun onResponse(call: Call<Racun>, response: Response<Racun>) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<Racun>, t: Throwable) {
                    printCustom("RestApiServiceRacun: $t")
                    onResult(null)
                }

            }
        )
    }

}