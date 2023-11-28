package banks.api.retrofit.banks.erste

import banks.api.retrofit.banks.erste.data.ErsteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tools.printCustom

class RestApiServiceErste {

    fun getCurs(onResult: (ErsteResponse?) -> Unit){
        val retrofit = ServiceBuilderErste.buildService(RetrofitApiErste::class.java)
        retrofit.getInfo().enqueue(
            object : Callback<ErsteResponse> {
                override fun onFailure(call: Call<ErsteResponse>, t: Throwable) {
                    printCustom(t.toString())
                    onResult(null)
                }
                override fun onResponse(call: Call<ErsteResponse>, response: Response<ErsteResponse>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }

}