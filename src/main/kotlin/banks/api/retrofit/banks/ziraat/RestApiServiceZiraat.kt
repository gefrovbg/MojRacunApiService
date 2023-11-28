package banks.api.retrofit.banks.ziraat

import banks.api.retrofit.banks.ziraat.data.ZiraatResponse
import org.springframework.stereotype.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tools.printCustom

@Service
class RestApiServiceZiraat {

    fun getCurs(onResult: (ZiraatResponse?) -> Unit){
        val retrofit = ServiceBuilderZiraat.buildService(RetrofitApiZiraat::class.java)
        retrofit.getInfo().enqueue(
            object : Callback<ZiraatResponse> {
                override fun onFailure(call: Call<ZiraatResponse>, t: Throwable) {
                    printCustom(t.toString())
                    onResult(null)
                }
                override fun onResponse(call: Call<ZiraatResponse>, response: Response<ZiraatResponse>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }

}