package banks.api.retrofit.banks.nlb

import banks.api.retrofit.banks.nlb.data.NLBResponse
import org.springframework.stereotype.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tools.printCustom

@Service
class RestApiServiceNLB {

    fun getCurs(date: String, onResult: (NLBResponse?) -> Unit){
        val retrofit = ServiceBuilderNLB.buildService(RetrofitApiNLB::class.java)
        retrofit.getInfo(date = date).enqueue(
            object : Callback<NLBResponse> {
                override fun onFailure(call: Call<NLBResponse>, t: Throwable) {
                    printCustom(t.toString())
                    onResult(null)
                }
                override fun onResponse(call: Call<NLBResponse>, response: Response<NLBResponse>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }

}